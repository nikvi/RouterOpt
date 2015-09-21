package routing;

import data.RoutePOJO;
import de.cm.osm2po.model.LatLons;
import de.cm.osm2po.routing.DefaultRouter;
import de.cm.osm2po.routing.Graph;
import de.cm.osm2po.routing.RoutingResultSegment;
import data.MyLatLon;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by nikki on 3/17/2015.
 *
 */
public class MyRouter {
    private Graph graph;
    private DefaultRouter router;
    private Properties properties;

    public MyRouter(File graphFile) {
        this.graph = new Graph(graphFile);
        this.router = new DefaultRouter();
        this.properties = this.getParams();
    }

    private Properties getParams() {
        Properties params = new Properties();
        params.setProperty("findShortestPath", "true");
        params.setProperty("ignoreRestrictions", "true");
        params.setProperty("ignoreOneWays", "true");
        params.setProperty("heuristicFactor", "0.0");
        return params;

    }

    public boolean checkPath(MyLatLon source,MyLatLon target){
        int sourceID = graph.findClosestVertexId((float)source.getLat(), (float)source.getLon());
        int targetID = graph.findClosestVertexId((float)target.getLat(), (float)target.getLon());
        router.reset();
        if(sourceID!=-1 && targetID != -1) {
            int[] path = router.findPath(
                    graph, sourceID, targetID,45.0f, properties);
            List<RoutingResultSegment> routePoints = new ArrayList<RoutingResultSegment>();
            if (path != null) { // Found!
               return true;
                }
            }
        return false;
    }

    public RoutePOJO getRoute(MyLatLon source,MyLatLon target) {

        int sourceID = graph.findClosestVertexId((float)source.getLat(), (float)source.getLon());
        int targetID = graph.findClosestVertexId((float)target.getLat(), (float)target.getLon());
        router.reset();
        RoutePOJO rtpj=null;
        if(sourceID!=-1 && targetID != -1) {
            int[] path = router.findPath(
                    graph, sourceID, targetID, Float.MAX_VALUE, properties);
            List<RoutingResultSegment> routePoints = new ArrayList<RoutingResultSegment>();
            if (path != null) { // Found!
                for (int i = 0; i < path.length; i++) {
                    RoutingResultSegment rrs =
                            graph.lookupSegment(path[i]);
                    routePoints.add(rrs);
                }
            }
            rtpj = getTotalPath(source,target,routePoints);

        }else {
            rtpj = new RoutePOJO(source,target);
        }
        return rtpj;
    }

    private RoutePOJO getTotalPath(MyLatLon source, MyLatLon target, List<RoutingResultSegment> list){
        float totalLength =0;
        //preserve the order in which added
        LinkedList<LatLons> path = new LinkedList<LatLons>();
        for(RoutingResultSegment rs : list){
            totalLength += rs.getKm();
            path.add(rs.getCoords());
        }
        RoutePOJO pj = new RoutePOJO(totalLength,null);
        return pj;

    }


}
