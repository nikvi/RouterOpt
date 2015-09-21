package routing;

import data.Path;
import data.RoutingHelper;
import grid.GeoFunctions;
import data.MyLatLon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by nikki on 6/2/2015.
 * In RouterOpt for routing
 */
public class SimpleCache {
    private HashMap<Integer, Path> sGrid;
    private ArrayList<MyLatLon> centroids;
    private SimpleRouter router;

    public int getSize(){
        return sGrid.size();
    }


    public SimpleCache(HashMap<Integer, Path> grid, ArrayList<MyLatLon> centroids, SimpleRouter router) {
        this.centroids = centroids;
        this.sGrid = grid;
        this.router = router;
    }

    public Path getCachedPath(MyLatLon source, MyLatLon target) {
        Path combinationPath = null;
        MyLatLon closestStart = GeoFunctions.getNearestNeighbour(source, this.centroids);
        MyLatLon closestEnd = GeoFunctions.getNearestNeighbour(target, this.centroids);
        if (!closestStart.equals(closestEnd)) {
            double distancePoint = GeoFunctions.getDistanceMeters(source, target);
            double distanceCentroid = GeoFunctions.getDistanceMeters(closestStart, closestEnd);
            double newDistance = 0;
            double percentEucDist = (1.4 * distancePoint);
            if (closestEnd.getEuDistance() != 0 && closestStart.getEuDistance() != 0) {
                newDistance = distanceCentroid + closestEnd.getEuDistance() + closestStart.getEuDistance();
            }
            if (newDistance <= percentEucDist && newDistance != 0) {
                Integer key1 = GeoFunctions.getLatLonHashCode(closestStart, closestEnd);
                Integer key2 = GeoFunctions.getLatLonHashCode(closestEnd, closestStart);
                if (sGrid!= null && !sGrid.isEmpty()) {
                    Set<Integer> keys = new TreeSet<Integer>(this.sGrid.keySet());
                    Path cachedPath = null;
                    if (keys.contains(key1)) {
                        //System.out.println("In key one!!!!!!");
                        cachedPath = sGrid.get(key1);
                    } else if (keys.contains(key2)) {
                        //System.out.println("In key two!!!!!!");
                        cachedPath = sGrid.get(key2);
                    }
                    if(cachedPath!=null){
                        Path startLeg = null;
                        Path endLeg = null;
                        if (!source.equals(closestStart)) {
                            startLeg = router.getSimpleRoute(source, closestStart);
                        }
                        if (!target.equals(closestEnd)) {
                            endLeg = router.getSimpleRoute(closestEnd, target);
                        }
                        combinationPath = RoutingHelper.getCombinedPaths(startLeg,cachedPath,endLeg);
                    }
                }
            }
        }
        if(combinationPath==null){
            combinationPath = router.getSimpleRoute(source, target);
            combinationPath.setCalculated(Boolean.TRUE);
        }
        return combinationPath;
    }

}
