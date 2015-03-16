package testRoute;

import de.cm.osm2po.model.LatLon;
import routing.RoutePOJO;
import routing.Routes;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by nikki on 3/17/2015.
 * In RouterOpt for testRoute
 */
public class Starter {

    public static void main(String args[]) {
        File graphFile = new File(args[0]);
        //File centroids = new File(args[1]);
        //maybe read it in as a  list of centroids
        //3rdfile which contains the Random points
        Routes router =new Routes(graphFile);
        //reads the centroids from file and creates hashmap of starting points as key and route data as map
        Map<LatLon,TreeSet<RoutePOJO>> data = new HashMap<LatLon,TreeSet<RoutePOJO>>();
        //same reading and adding
        String sp = "-37.870868";
        LatLon start = new LatLon(-Long.parseLong(sp),Long.parseLong("144.768562f"));
        TreeSet<RoutePOJO>  targetData = new TreeSet<RoutePOJO>();
        //for every other centroid in data;
        LatLon target = new LatLon(-Long.parseLong("37.870868"),Long.parseLong("144.768562f")) ;
        targetData.add(router.getRoute(start, target));
        //at end of parsing through a centroid
        data.put(start,targetData);

    }
}
