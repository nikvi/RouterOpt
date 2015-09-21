package dallas;

import grid.BoundingBox;
import grid.GeoFunctions;
import data.MyLatLon;

/**
 * Created by nikki on 5/11/2015.
 * In RouterOpt for dallas
 */
public class TestData {
    public static void main(String[] args){
        MyLatLon source = new MyLatLon(39.9529f,-105.2724f);
        MyLatLon target = new MyLatLon(32.924f,-96.416f);
        double dist = GeoFunctions.getDistanceMeters(source,target);
        MyLatLon point =GeoFunctions.getLatLonFromDistance(source,50000,90);

        System.out.println(point);
        System.out.println(dist/1000);
        BoundingBox bx = new BoundingBox(39.5032f,-105.2724f,39.9529f,-104.6858f, 5, 5);
        //ArrayList<MyLatLon> cellCentroids = bx.getCentroids();
    }
}
