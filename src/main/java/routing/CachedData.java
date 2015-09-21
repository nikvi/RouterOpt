package routing;

import data.LatLonCode;
import data.RoutePOJO;
import grid.GeoFunctions;
import data.MyLatLon;

import java.util.*;

/**
 * Created by nikki on 5/7/2015.
 * In RouterOpt for routing
 */
public class CachedData {
    private HashMap<LatLonCode, RoutePOJO> grid;
    private Set<LatLonCode> keys;
    private ArrayList<MyLatLon> centroids;
    private MyRouter router;

    @Override
    public String toString() {
        return "CachedData{" +
                "routerGridSimple=" + grid +
                ", centroids=" + centroids +
                ", router=" + router +
                '}';
    }

    public int size() {
        int m = this.grid.size();
        return m;
    }

    public HashMap<LatLonCode, RoutePOJO> getRouterGridSimple() {
        return grid;
    }

    public void setRouterGridSimple(HashMap<LatLonCode, RoutePOJO> routerGridSimple) {
        this.grid = routerGridSimple;
    }

    public ArrayList<MyLatLon> getCentroids() {
        return centroids;
    }

    public void setCentroids(ArrayList<MyLatLon> centroids) {
        this.centroids = centroids;
    }

    public CachedData(HashMap<LatLonCode, RoutePOJO> grid, ArrayList<MyLatLon> centroids, MyRouter router) {
        this.centroids = centroids;
        this.grid = grid;
        this.router = router;
        this.keys = new HashSet<LatLonCode>(this.grid.keySet());

    }

    public RoutePOJO getCachedRoute(MyLatLon source, MyLatLon target) {
        RoutePOJO combinedResult = null;
        MyLatLon closestStart = GeoFunctions.getNearestNeighbour(source, this.centroids);
        MyLatLon closestEnd = GeoFunctions.getNearestNeighbour(target, this.centroids);
        //System.out.println("Closest end centroid is: " + closestEnd);
        if (!closestStart.equals(closestEnd)) {
            double distancePoint = GeoFunctions.getDistanceMeters(source, target);
            double distanceCentroid = GeoFunctions.getDistanceMeters(closestStart, closestEnd);
            double newDistance = 0;
            double percentEucDist = (1.3* distancePoint);
            if (closestEnd.getEuDistance() != 0 && closestStart.getEuDistance() != 0) {
                newDistance = distanceCentroid + closestEnd.getEuDistance() + closestStart.getEuDistance();
            }
            //will test only if calculated route is not greater than 25percent  actual route
            if (newDistance <= percentEucDist && newDistance != 0) {
                LatLonCode key1 = new LatLonCode(closestStart,closestEnd);
                LatLonCode key2 = new LatLonCode(closestEnd,closestStart);
                //System.out.println("All keys " + keys.toString());
                if (grid != null && !grid.isEmpty()) {
                    RoutePOJO cachedRoute = null;
                    if (keys.contains(key1)) {
                        //System.out.println("In key one!!!!!!");
                        cachedRoute = grid.get(key1);
                    } else if (keys.contains(key2)) {
                        //System.out.println("In key two!!!!!!");
                        cachedRoute = grid.get(key2);
                    }
                    if (cachedRoute != null) {
                        //System.out.println("Combining results");
                        RoutePOJO startLeg = null;
                        RoutePOJO endLeg = null;
                        if (!source.equals(closestStart)) {
                            startLeg = router.getRoute(source, closestStart);
                        }
                        if (!target.equals(closestEnd)) {
                            endLeg = router.getRoute(closestEnd, target);
                        }
                        if (startLeg == null && endLeg == null) {
                            combinedResult = cachedRoute;
                        } else if (startLeg == null & endLeg.getTotalLength() > 0) {
                            combinedResult = cachedRoute.combine(endLeg);
                        } else if (endLeg == null & startLeg.getTotalLength() > 0) {
                            combinedResult = startLeg.combine(cachedRoute);
                        } else if (startLeg.getTotalLength() > 0 && endLeg.getTotalLength() > 0 && cachedRoute.getTotalLength() > 0) {
                            combinedResult = startLeg.combine(cachedRoute, endLeg);
                        }
                    }
                }
            }
        }
        if (combinedResult == null) {
            combinedResult = router.getRoute(source, target);
            combinedResult.setCacheCalc(Boolean.FALSE);
        }

        return combinedResult;
    }
}
