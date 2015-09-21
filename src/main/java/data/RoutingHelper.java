package data;

import de.cm.osm2po.routing.Graph;
import de.cm.osm2po.routing.RoutingResultSegment;

import java.util.LinkedList;

/**
 * Created by nikki on 6/2/2015.
 * In RouterOpt for data
 */
public class RoutingHelper {

    public static Path getCombinedPaths(Path startLeg, Path cachedPath,Path endLeg){
        Path totalPath = null;
        float totalDistance = 0.0f;
        boolean start = Boolean.FALSE;
        boolean end = Boolean.FALSE;
        LinkedList<Integer>  pathIds = new LinkedList<Integer>();
        if(startLeg!=null && startLeg.getLength()>0.0f){
            totalDistance += startLeg.getLength();
            pathIds.addAll(startLeg.getPathIds());
            start = Boolean.TRUE;
        }
        if(cachedPath !=null && cachedPath.getLength()>0.0f){
            totalDistance += cachedPath.getLength();
            pathIds.addAll(cachedPath.getPathIds());
        }
        if(endLeg !=null && endLeg.getLength()>0.0f){
            totalDistance += endLeg.getLength();
            pathIds.addAll(endLeg.getPathIds());
            end = Boolean.TRUE;
        }
        if(start || end) {
            totalPath = new Path(pathIds, totalDistance);
        }

        return totalPath;
    }

    public void getPathFromIDs(LinkedList<Integer> pathIDs,Graph graph) {
        if (pathIDs != null) { // Found!
            for (Integer pathId : pathIDs) {
                RoutingResultSegment rrs = graph.lookupSegment(pathId);
                int segId = rrs.getId();
                int from = rrs.getSourceId();
                int to = rrs.getTargetId();
                String segName = rrs.getName().toString();
                System.out.println(from + "-" + to + "  " + segId + "/" + pathId + " " + segName);
            }
        }
    }
}
