package data;

import de.cm.osm2po.model.LatLons;

import java.util.List;

/**
 * Created by nikki on 3/17/2015.
 */
public class RoutePOJO implements Comparable<RoutePOJO> {

    //in kms
    private Float totalLength;
    //object contains the route as series of LatLon points
    private List<LatLons> route;
    private MyLatLon startPoint;
    private MyLatLon endPoint;
    private Boolean cacheCalc;


    public RoutePOJO(Float totalLength, List<LatLons> route) {
        super();
        this.totalLength = totalLength;
        this.route = route;
        this.cacheCalc = Boolean.TRUE;
    }

    public RoutePOJO(Float totalLength, List<LatLons> route,MyLatLon source, MyLatLon target) {
        super();
        this.totalLength = totalLength;
        this.route = route;
        this.startPoint = source;
        this.endPoint = target;
        this.cacheCalc = Boolean.TRUE;
    }
    public RoutePOJO(MyLatLon startPoint,MyLatLon endPoint) {
        super();
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.totalLength = -1.0f;
        this.route =null;
        this.cacheCalc = Boolean.TRUE;
    }

    public RoutePOJO(){
        this.cacheCalc = Boolean.TRUE;
    }

    public Boolean isCacheCalc() {
        return cacheCalc;
    }

    public void setCacheCalc(Boolean cacheCalc) {
        this.cacheCalc = cacheCalc;
    }
    public Float getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Float totalLength) {
        this.totalLength = totalLength;
    }

    public void setRoute(List<LatLons> route) {
        this.route = route;
    }

    public void setStartPoint(MyLatLon startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(MyLatLon endPoint) {
        this.endPoint = endPoint;
    }

    public MyLatLon getStartPoint() {
        return startPoint;
    }

    public MyLatLon getEndPoint() {
        return endPoint;
    }

    public List<LatLons> getRoute() {
        return route;
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

    @Override
    public int compareTo(RoutePOJO o) {
        if (endPoint.getLat() > o.getEndPoint().getLat())
            return 1;
        else if (endPoint.getLat()== o.getEndPoint().getLat()){
            return 0;
        }
        else
            return -1;
    }

    public RoutePOJO combine(RoutePOJO pj1){
        float dist = this.getTotalLength()+ pj1.getTotalLength();
        // this.getRoute();
        List<LatLons> lst = null;
        if(lst!=null){
            if(pj1.getRoute()!=null){
                lst.addAll(pj1.getRoute());
            }
        }
        RoutePOJO combined = new RoutePOJO(dist,lst);
        //combined.setStartPoint(this.startPoint);
        //combined.setEndPoint(pj1.endPoint);
        return combined;
    }

    public RoutePOJO combine(RoutePOJO pj1, RoutePOJO pj2){
        float dist = this.getTotalLength()+ pj1.getTotalLength()+ pj2.getTotalLength();
        List<LatLons> lst = null;
                //this.getRoute();
        if(lst!=null){
            if(pj1.getRoute()!=null && pj2.getRoute()!=null){
                lst.addAll(pj1.getRoute());
                lst.addAll(pj2.getRoute());
            }
        }
        RoutePOJO combined = new RoutePOJO(dist,lst);
        //combined.setStartPoint(this.startPoint);
        //combined.setEndPoint(pj2.endPoint);
        return combined;
    }
}
