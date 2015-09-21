
package algoTest;

import dallas.RandomGenerator;
import dallas.Test;
import data.RoutePOJO;
import data.MyLatLon;
import routing.MyRouter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by nikki on 6/3/2015.
 * In RouterOpt for algoTest
 */

public class Output {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id,source,destination,length,time)";
    private static final Logger logger =
            Logger.getLogger(Test.class.getName());

    public static void writeToFile(List<RandomGenerator.QueryLatLon> queries, File graphFile, String outputFile) {
        FileWriter fileWriter = null;
        MyRouter router = new MyRouter(graphFile);
        try {
            fileWriter = new FileWriter(outputFile);
            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
            int count = 1;
            for (RandomGenerator.QueryLatLon query : queries) {
                MyLatLon source = query.source;
                MyLatLon target = query.target;
                fileWriter.append(String.valueOf(count));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(source.toString());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(target.toString());
                fileWriter.append(COMMA_DELIMITER);
                long startTimeCalc = System.currentTimeMillis();
                float calculatedLength = 0;
                RoutePOJO calculatedRoute = router.getRoute(source, target);
                long endTimeCalc = System.currentTimeMillis();
                if (calculatedRoute != null) {
                    calculatedLength = calculatedRoute.getTotalLength();
                }
                long calculatedRouteTime = (endTimeCalc - startTimeCalc);
                fileWriter.append(String.valueOf(calculatedLength));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(calculatedRouteTime));
                fileWriter.append(NEW_LINE_SEPARATOR);
                count++;
            }
            logger.info("CSV file was created successfully !!!");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        }
    }
}



