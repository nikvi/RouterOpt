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
    //<?xml version='1.0' encoding='UTF-8'?>
    //<osm version="0.6" generator="Osmosis 0.43.1">
    //<bounds minlon="144.26600" minlat="-38.55200" maxlon="145.81000" maxlat="-37.36500" origin="http://www.openstreetmap.org/api/0.6"/>

    public static void main(String args[]){
      File graphFile = new File(args[0]);
      System.out.println(graphFile.getAbsoluteFile());
      Graph graph = new Graph(graphFile);
      DefaultRouter router = new DefaultRouter();
      //int sourceId = graph.findClosestVertexId(-37.870868f,144.768562f);
      //int targetId = graph.findClosestVertexId(-37.786461f,144.831940f);
        //int source = graph.findClosestVertexId(-38.55200f,144.26600f);
        int sourceID = graph.findClosestVertexId(32.9905589f,-97.5216696f);
        int targetId = graph.findClosestVertexId(33.3963402f,-96.1203403f);
        System.out.println(sourceID);

/*
// Somewhere in Hamburg

        int sourceId = graph.findClosestVertexId(53.5f, 10.0f);
      int targetId = graph.findClosestVertexId(53.4f, 10.1f);
*/
// additional params for DefaultRouter
      Properties params = new Properties();
      params.setProperty("findShortestPath", "false");
      params.setProperty("ignoreRestrictions", "true");
      params.setProperty("ignoreOneWays", "true");
      params.setProperty("heuristicFactor", "0.0"); // 0.0 Dijkstra, 1.0 good A*

      int[] path = router.findPath(
              graph, sourceID, targetId, Float.MAX_VALUE, params);

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
      }else{
          System.out.println("No path found");
      }

      graph.close();
  }

}
