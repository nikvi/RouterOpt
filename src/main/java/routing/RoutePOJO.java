package routing;

import de.cm.osm2po.model.LatLon;
import de.cm.osm2po.model.LatLons;

import java.util.LinkedHashSet;

/**
 * Created by nikki on 3/17/2015.
 */
public class RoutePOJO {
    //in kms
    private Float totalLength;
    //object contains the route as series of LatLon points
    private LinkedHashSet<LatLons> route;
    private LatLon startPoint;
    private LatLon endPoint;

    public RoutePOJO(Float totalLength, LinkedHashSet<LatLons> route) {
        super();
        this.totalLength = totalLength;
        this.route = route;
    }

    public RoutePOJO(){

    }

    public Float getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Float totalLength) {
        this.totalLength = totalLength;
    }

    public void setRoute(LinkedHashSet<LatLons> route) {
        this.route = route;
    }

    public void setStartPoint(LatLon startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(LatLon endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "RoutePOJO{" +
                "totalLength=" + totalLength +
                ", route=" + route +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                '}';
    }
}
