package grid;

import data.MyLatLon;

/**
 * Created by nikki on 3/22/2015.
 * In RouterOpt for grid
 */
public class Cell {
    MyLatLon min;
    MyLatLon max;
    MyLatLon centroid;

    public Cell(MyLatLon min, MyLatLon max) {
        this.min = min;
        this.max = max;
        this.centroid = this.calculateCentroid(min, max);
    }

    public MyLatLon getMin() {
        return min;
    }

    public void setMin(MyLatLon min) {
        this.min = min;
    }

    public MyLatLon getMax() {
        return max;
    }

    public void setMax(MyLatLon max) {
        this.max = max;
    }

    public MyLatLon getCentroid() {
        return centroid;
    }

    public void setCentroid(MyLatLon centroid) {
        this.centroid = centroid;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "min=" + min +
                ", max=" + max +
                ", centroid=" + centroid +
                '}';
    }

    private MyLatLon calculateCentroid(MyLatLon min, MyLatLon max){
        double  centroidLat = min.getLat() + ((max.getLat() - min.getLat())/2);
        double centroidLon = min.getLon() + ((max.getLon() - min.getLon())/2);
        MyLatLon centroid = new MyLatLon(centroidLat,centroidLon);
        return centroid;

    }
}
