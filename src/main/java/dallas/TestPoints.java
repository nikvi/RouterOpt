package dallas;

import de.cm.osm2po.routing.Graph;
import de.cm.osm2po.routing.GraphHeader;
import grid.BoundingBox;
import grid.GeoFunctions;
import data.MyLatLon;
import routing.MyRouter;
import data.RoutePOJO;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nikki on 5/18/2015.
 * In RouterOpt for dallas
 */
public class TestPoints {
    public static void main(String[] args)throws Exception{
        //BoundingBox bx = BBoxData.getDallasBoundingBox(5);
        //ArrayList<MyLatLon> centroids = bx.getCentroids();
        //TODO Remove File
        //RandomGenerator generator = new RandomGenerator(1,BBoxData.DENVER_MIN_LAT,BBoxData.DENVER_MAX_LAT,BBoxData.DENVER_MIN_LON,BBoxData.DENVER_MAX_LON, new File("st"));
        File graphFile = new File(args[0]);
        //MyRouter  router = new MyRouter(graphFile);
        Graph graph = new Graph(graphFile);
        GraphHeader gh = new GraphHeader(graphFile);
        if(gh!=null){
            System.out.println(gh.getNumberOfVertices());
        }
        System.out.println("Number of lats are "+ graph.getLats().length);
        int[] tr = graph.getEdgeIds();
        System.out.println(tr.length);
       /* ArrayList<RandomGenerator.QueryLatLon> randomPoints = generator.getRandData();
        for (RandomGenerator.QueryLatLon query : randomPoints) {
            MyLatLon source = query.source;
            MyLatLon target = query.target;
            RoutePOJO routeData = router.getRoute(source,target);
            System.out.println("Starting point is:"+ source);
            System.out.println("End point point is:"+ target);
            MyLatLon closestStart = GeoFunctions.getNearestNeighbour(source, centroids);
            System.out.println("Closest startpoint is"+ closestStart);
            MyLatLon closestEnd = GeoFunctions.getNearestNeighbour(target, centroids);
            System.out.println("Closest endpoint is"+ closestEnd);
         }
*/
        }

    }
