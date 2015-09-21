package algoTest;

import dallas.BBoxData;
import dallas.RandomGenerator;
import dallas.Test;
import data.RoutePOJO;
import grid.BoundingBox;
import data.MyLatLon;
import routing.CachedData;
import routing.GridToRouteConvert;
import routing.MyRouter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by nikki on 6/3/2015.
 * In RouterOpt for algoTest
 */
public class OutputCached {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id,source,destination,length,time)";
    private static final Logger logger =
            Logger.getLogger(Test.class.getName());

    public static void writeToFile(List<RandomGenerator.QueryLatLon> queries, String graphFile, String outputFile,int cellNum) {
        FileWriter fileWriter = null;
        GridToRouteConvert gridGenerator = createGrid(graphFile,cellNum);
        MyRouter router = gridGenerator.getRouter();
        CachedData cache = gridGenerator.getCache();
        logger.info("Size of cache is:"+cache.size());
        logger.info("Start testing points");
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
                long startTimeCache = System.currentTimeMillis();
                float cachedLength =0;
                RoutePOJO cacheRoute = cache.getCachedRoute(source, target);
                long endTimeCache =  System.currentTimeMillis();
                if (cacheRoute.isCacheCalc()) {
                    cachedLength = cacheRoute.getTotalLength();
                }
                else{
                    cachedLength =(-1.0f*cacheRoute.getTotalLength());
                }
                // divide by 1000000 if nanotime
                long cachedRouteTime =  (endTimeCache - startTimeCache);
                fileWriter.append(String.valueOf(cachedLength));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(cachedRouteTime));
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
    public static GridToRouteConvert createGrid(String dataFile,int cellNum){
        File graphFile = new File(dataFile);
        //can change to required box Dallas
        logger.info("Creating graph for Dallas");
        BoundingBox bx = BBoxData.getDenverBoundingBox(cellNum);
        ArrayList<MyLatLon> cellCentroids = bx.getCentroids();
        //use simple paths
        GridToRouteConvert cache = new GridToRouteConvert(graphFile, cellCentroids,Boolean.FALSE);
        return cache;
    }
}

