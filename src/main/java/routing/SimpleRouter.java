package routing;

import data.Path;
import de.cm.osm2po.routing.DefaultRouter;
import de.cm.osm2po.routing.Graph;
import de.cm.osm2po.routing.RoutingResultSegment;
import data.MyLatLon;

import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by nikki on 6/2/2015.
 * In RouterOpt for routing
 */
public class SimpleRouter {
    private Graph graph;
    private DefaultRouter router;
    private Properties properties;

    public SimpleRouter(Graph graph) {
        this.graph = graph;
        this.router = new DefaultRouter();
        this.properties = this.getParams();
    }

    //A* with heuristic of zero is Dijkstras algorithm
    private Properties getParams() {
        Properties params = new Properties();
        params.setProperty("findShortestPath", "true");
        params.setProperty("ignoreRestrictions", "true");
        params.setProperty("ignoreOneWays", "true");
        params.setProperty("heuristicFactor", "0.0");
        return params;
    }

    public Path getSimpleRoute(MyLatLon source, MyLatLon target){
        int sourceID = graph.findClosestVertexId((float)source.getLat(), (float)source.getLon());
        int targetID = graph.findClosestVertexId((float)target.getLat(), (float)target.getLon());
        router.reset();
        LinkedList<Integer> route = new LinkedList<Integer>();
        float distance = 0.0f;
        if(sourceID!=-1 && targetID != -1) {
            int[] path = router.findPath(
                    graph, sourceID, targetID, Float.MAX_VALUE, this.properties);
            if (path != null) { // Found!
                for (int i = 0; i < path.length; i++) {
                    RoutingResultSegment rrs = graph.lookupSegment(path[i]);
                    distance += rrs.getKm();
                    route.add(path[i]);
                }
            }
        }
        Path ssp = new Path(route,distance);
        return ssp;
    }
}
