package testRoute;

import grid.BoundingBox;
import data.MyLatLon;
import grid.OSMData;
import routing.GridToRouteConvert;
import data.RoutePOJO;
import routing.MyRouter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikki on 3/17/2015.
 * In RouterOpt for testRoute
 */
public class Starter {

    public static void main(String args[]) throws Exception {
        File graphFile = new File(args[0]);
        long startTime = System.currentTimeMillis();
        BoundingBox bx = new BoundingBox(3, 3);
        ArrayList<MyLatLon> cellCentroids = bx.getCentroids();
        GridToRouteConvert cache = new GridToRouteConvert(graphFile, cellCentroids,Boolean.FALSE);
        MyRouter router = cache.getRouter();
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        List<MyLatLon> samplePoints = new ArrayList<MyLatLon>();
        //two methods to get data
        for (int i = 0; i < 2; i++) {
            double lat1 = Math.random() * (OSMData.maxLat - OSMData.minLat) + OSMData.minLat;
            double lon1 = Math.random() * (OSMData.maxLon - OSMData.minLon) + OSMData.minLon;
            samplePoints.add(new MyLatLon(lat1, lon1));
        }
        RoutePOJO rpj = cache.getCache().getCachedRoute(samplePoints.get(0), samplePoints.get(1));
        RoutePOJO rpj2 = router.getRoute(samplePoints.get(0), samplePoints.get(1));
        if (rpj != null) {
            System.out.println("Cached length is :" + rpj.getTotalLength());
        }
        if (rpj2 != null)
            System.out.println("Total length is: " + rpj2.getTotalLength());


    }
}
