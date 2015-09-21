package dallas;

import data.MyLatLon;
import routing.MyRouter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nikki on 5/7/2015.
 * In RouterOpt for dallas
 * Generates random queries for Dallas
 */
public class RandomGenerator {

     ArrayList<QueryLatLon> randomQueries;
     int queryCount;
     double minLat,maxlat,minLon,maxLon;
     MyRouter router;




    public static class QueryLatLon implements Serializable{
        public MyLatLon source;
        public MyLatLon target;

        @Override
        public String toString() {
            return "QueryLatLon{" +
                    "source=" + source +
                    ", target=" + target +
                    '}';
        }

        public QueryLatLon(MyLatLon source, MyLatLon target) {
            this.source = source;
            this.target = target;
        }
    }

    public RandomGenerator(int numQuery, double minLat, double maxLat, double minLon, double maxLon, File gFile){
        this.randomQueries = new ArrayList<QueryLatLon>();
        this.queryCount = numQuery;
        this.minLat =minLat;
        this.minLon = minLon;
        this.maxlat = maxLat;
        this.maxLon = maxLon;
        this.router = new MyRouter(gFile);
        this.populateData();
    }

    public ArrayList<QueryLatLon> getRandData() {
        return randomQueries;
    }


    /**
     * Returns a pseudo-random number between min lat and max lat, inclusive.
      *
     * @return Double between min and max, inclusive.
     * @see java.util.Random#nextDouble
     */
    public  double randLat() {
        Random rand = new Random();
        double randomLat = this.minLat + (this.maxlat - this.minLat) * rand.nextDouble();
        return randomLat;
    }

    public  double randLon() {
        Random rand = new Random();
        double randomLon = this.minLon+ (this.maxLon- this.minLon) * rand.nextDouble();
        return randomLon;
    }

    public void populateData(){
        int j=0;
         for(int i=0;j<queryCount;i++){
             MyLatLon source =  new MyLatLon(randLat(),randLon());
             MyLatLon target = new MyLatLon(randLat(),randLon());
             boolean checkPath = router.checkPath(source,target);
             if(checkPath) {
                 QueryLatLon query = new QueryLatLon(source, target);
                 randomQueries.add(j, query);
                 j++;
             }
         }

    }
}