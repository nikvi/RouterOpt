package routing;

import de.cm.osm2po.model.LatLon;
import de.cm.osm2po.model.LatLons;
import de.cm.osm2po.routing.DefaultRouter;
import de.cm.osm2po.routing.Graph;
import de.cm.osm2po.routing.RoutingResultSegment;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

/**
 * Created by nikki on 3/17/2015.
 *
 */
public class Routes {

    private Graph graph;
    private DefaultRouter router;
    private Properties properties;

    public Routes(File graphFile) {
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


    public RoutePOJO getRoute(LatLon source,LatLon target) {

        int sourceID = graph.findClosestVertexId((float)source.getLat(), (float)source.getLon());
        int targetID = graph.findClosestVertexId((float)target.getLat(), (float)target.getLon());
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
        RoutePOJO rtPj = getTotalPath(routePoints);
        rtPj.setStartPoint(source);
        rtPj.setEndPoint(target);
        return rtPj;
    }

    private RoutePOJO getTotalPath(List<RoutingResultSegment> list){
        float totalLength =0;
        //preserve the order in which added
        LinkedHashSet<LatLons>  path = new LinkedHashSet<LatLons>();
        for(RoutingResultSegment rs : list){
            totalLength += rs.getKm();
            path.add(rs.getCoords());
        }
        RoutePOJO pj = new RoutePOJO();
        return pj;

    }


}
