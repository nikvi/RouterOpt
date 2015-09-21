package dallas;

import data.MyLatLon;
import routing.*;
import data.RoutePOJO;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by nikki on 5/7/2015.
 * In RouterOpt for dallas
 */
public class OutputGenerator {
    private static final Logger logger =
            Logger.getLogger(Test.class.getName());
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id,source,destination,cachedLength(km),timeCached(ms),calculatedLength(km),timeCalculated(ms)";

    public static void writeToFile(String fileName,int queryCount, GridToRouteConvert cacheData ){
        FileWriter fileWriter = null;
        GridToRouteConvert gridGenerator = cacheData;
        MyRouter router = gridGenerator.getRouter();
        CachedData cache = gridGenerator.getCache();
        logger.info("Size of cache is:"+cache.size());
        //TODO remove is wrong
        File f = new File(fileName);
        RandomGenerator generator = new RandomGenerator(queryCount,BBoxData.DENVER_MIN_LAT,BBoxData.DENVER_MAX_LAT,BBoxData.DENVER_MIN_LON,BBoxData.DENVER_MAX_LON,f);
        ArrayList<RandomGenerator.QueryLatLon> randomPoints = generator.getRandData();
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
            int count =1;
            for (RandomGenerator.QueryLatLon query : randomPoints) {
                MyLatLon source = query.source;
                MyLatLon target = query.target;
                fileWriter.append(String.valueOf(count));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(source.toString());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(target.toString());
                fileWriter.append(COMMA_DELIMITER);

                //get from cache!! //System.nanoTime();
                long startTimeCache =  System.currentTimeMillis();
                float cachedLength =0;
                RoutePOJO cacheRoute = cache.getCachedRoute(source, target);
                long endTimeCache =  System.currentTimeMillis();
                //will be zero if no route present
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
                fileWriter.append(COMMA_DELIMITER);

                //calculating directly
                long startTimeCalc =  System.currentTimeMillis();
                float calculatedLength =0;
                RoutePOJO calculatedRoute = router.getRoute(source,target);
                long endTimeCalc =  System.currentTimeMillis();
                if (calculatedRoute != null) {
                    calculatedLength = calculatedRoute.getTotalLength();
                }
                long calculatedRouteTime =  (endTimeCalc - startTimeCache);
                fileWriter.append(String.valueOf(calculatedLength));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(calculatedRouteTime));
                fileWriter.append(NEW_LINE_SEPARATOR);
                count++;
            }
            logger.info("CSV file was created successfully !!!");
            fileWriter.flush();
            fileWriter.close();
        }
        catch(Exception e){
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        }
    }
}

