package testRoute;

import de.cm.osm2po.routing.DefaultRouter;
import de.cm.osm2po.routing.Graph;
import de.cm.osm2po.routing.RoutingResultSegment;

import java.io.File;
import java.util.Properties;

/**
 * Created by nikki on 3/14/2015.
 * In CodeEvalChallenges for PACKAGE_NAME
 */
public class TestOSM2PO {
  public static void main(String args[]){
      File graphFile = new File(args[0]);
      System.out.println(graphFile.getAbsoluteFile());
      Graph graph = new Graph(graphFile);
      DefaultRouter router = new DefaultRouter();
      int sourceId = graph.findClosestVertexId(-37.870868f,144.768562f);
      int targetId = graph.findClosestVertexId(-37.786461f,144.831940f);

/*
// Somewhere in Hamburg

        int sourceId = graph.findClosestVertexId(53.5f, 10.0f);
      int targetId = graph.findClosestVertexId(53.4f, 10.1f);
*/
// additional params for DefaultRouter
      Properties params = new Properties();
      params.setProperty("findShortestPath", "true");
      params.setProperty("ignoreRestrictions", "true");
      params.setProperty("ignoreOneWays", "true");
      params.setProperty("heuristicFactor", "0.0"); // 0.0 Dijkstra, 1.0 good A*

      int[] path = router.findPath(
              graph, sourceId, targetId, Float.MAX_VALUE, params);

      if (path != null) { // Found!
          for (int i = 0; i < path.length; i++) {
              RoutingResultSegment rrs =
                      graph.lookupSegment(path[i]);
              int segId = rrs.getId();
              int from = rrs.getSourceId();
              int to = rrs.getTargetId();
              String segName = rrs.getName().toString();
              System.out.println(from + "-" + to + "  " + segId + "/" + path[i] + " " + segName);
          }
      }

      graph.close();
  }

}
