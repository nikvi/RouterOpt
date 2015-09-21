package dallas;

import grid.BoundingBox;
import data.MyLatLon;
import routing.GridToRouteConvert;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by nikki on 5/6/2015.
 * In RouterOpt for dallas
 */
public class Test {
    private static final Logger logger =
            Logger.getLogger(Test.class.getName());
    public static void main(String args[]) {
        // initalising the caches
        //5, 10,15, 20
        int cells = 5;
        GridToRouteConvert cache =createGrid(args[0], cells);
        logger.info("Start testing points");
        String fileName = System.getProperty("user.home")+"\\researchData\\denver" +
                "\\data20x20.csv";
        int query = 2000;
        long startTime =  System.currentTimeMillis();
        OutputGenerator.writeToFile(fileName,query,cache);
        long endTime =  System.currentTimeMillis();
        //for seconds
        long timeTakenToRun = (endTime -startTime)/1000;
        logger.info("It took:"+timeTakenToRun +" seconds to run experiment");

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
