package grid;

import data.MyLatLon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikki on 3/19/2015.
 * In RouterOpt for grid
 */
public class GeoFunctions {

    public static double getDegree(double x){
        return  x * 180 / Math.PI;
    }

    public static double toRadians(double x) {
        return x * Math.PI / 180;
    };



    public static double getDistanceMeters(MyLatLon source, MyLatLon target) {
        int R = 6378137; // Earth’s mean radius in meter
        double dLat = toRadians(target.getLat() - source.getLat());
        double dLong = toRadians(target.getLon() - source.getLon());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(toRadians(source.getLat())) * Math.cos(toRadians(target.getLat())) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d; // returns the distance in meter
    };

    /**
     * Returns the destination point from 'this' point having travelled the given distance on the
     * given initial bearing (bearing normally varies around path followed).
     *
     * @param   {number} distance - Distance travelled, in same units as earth radius (default: metres).
     * @param   {number} bearing - Initial bearing in degrees from north.
     * @param   {number} [radius=6371e3] - (Mean) radius of earth (defaults to radius in metres).
     * @returns {LatLon} Destination point.
     *
     * @example
     *     var p1 = new LatLon(51.4778, -0.0015);
     *     var p2 = p1.destinationPoint(7794, 300.7); // p2.toString(): 51.5135°N, 000.0983°W
     */
    public static  MyLatLon getLatLonFromDistance(MyLatLon source,double distance, double bearing){
        double  radius = 6371e3;
        double δ = distance/ radius;
        double θ = toRadians(bearing);
        double φ1 = toRadians(source.getLat());
        double  λ1 = toRadians(source.getLon());
        double φ2 = Math.asin( Math.sin(φ1)*Math.cos(δ) +
                Math.cos(φ1)*Math.sin(δ)*Math.cos(θ) );
        double λ2 = λ1 + Math.atan2(Math.sin(θ)*Math.sin(δ)*Math.cos(φ1),
                Math.cos(δ)-Math.sin(φ1)*Math.sin(φ2));
        λ2 = (λ2+3*Math.PI) % (2*Math.PI) - Math.PI; // normalise to -180..+180°

        return new MyLatLon(getDegree(φ2), getDegree(λ2));
    }
    //get pints on line
    public static List<MyLatLon> getPointsOnLine(int points,MyLatLon start,double distance, double bearing){
        List<MyLatLon> pointList = new ArrayList<MyLatLon>();
        double travelDist = distance;
        for(int i=0;i<points;i++){
            MyLatLon point =getLatLonFromDistance(start,travelDist,bearing);
            travelDist +=distance;
            pointList.add(point);
        }
        return pointList;
    }

    public static MyLatLon getNearestNeighbour(MyLatLon point,List<MyLatLon> list){
        double minDist = Double.MAX_VALUE;
        double currentDist = 0;
        MyLatLon closestPnt = null;
        for(MyLatLon data : list){
            currentDist = getDistanceMeters(point,data);
            if (currentDist<minDist){
                minDist = currentDist;
                closestPnt = data;
            }
        }
        return  new MyLatLon(closestPnt.getLat(),closestPnt.getLon(),minDist);
    }
    // see http://mathforum.org/library/drmath/view/55417.html
    public static  double getBearing(MyLatLon point1,MyLatLon point2){
        double phi1 = toRadians(point1.getLat());
        double phi2 = toRadians(point2.getLat());
        double lamda = toRadians(point1.getLon() - point2.getLon());
        double y = Math.sin(lamda)*Math.cos(phi2);
        double x = (Math.cos(phi1)*Math.sin(phi2)) - (Math.sin(phi1)*Math.cos(phi2)*Math.cos(lamda));
        double theta = Math.atan2(y,x);

        return (getDegree(theta)+360)%360;
    }
    public static Integer getLatLonHashCode(MyLatLon src, MyLatLon trg){
        int hash = 23;
        hash = ((hash << 5) * 37) ^ (src == null ? 0 : src.hashCode());
        hash = ((hash << 5) * 37) ^ (trg == null ? 0 : trg.hashCode());
        return hash;
    }
}
