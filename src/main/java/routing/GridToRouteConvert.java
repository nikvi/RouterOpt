package routing;

import data.LatLonCode;
import data.RoutePOJO;
import data.MyLatLon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * RouteDataStructure
 * Created by nikki on 3/22/2015.
 * In RouterOpt for routing
 */
public class GridToRouteConvert {
    //members
    private static final Logger logger =
            Logger.getLogger(GridToRouteConvert.class.getName());
    private MyRouter router;
    //private SimpleRouter simpleRouter;
    //private SimpleCache simpleCache;
    private CachedData cache;


    public GridToRouteConvert(File graphFile, ArrayList<MyLatLon> centroids,boolean isSimple) {
        if(isSimple){
           // Graph gph = new Graph(graphFile);
           // this.simpleRouter =new SimpleRouter(gph);
            //this.simpleCache = this.getRouteMapSimple(centroids);
        }
        else {
            this.router = new MyRouter(graphFile);
            this.cache = this.getRouteMap(centroids);
        }
    }

    /*public SimpleCache getSimpleCache(){
        return simpleCache;
    }

    public SimpleRouter getSimpleRouter(){
        return simpleRouter;
    }
    */

    public CachedData getCache() {
        return cache;
    }

    public MyRouter getRouter() {
        return router;
    }

    @Override
    public String toString() {
        return "GridToRouteConvert{" +
                "routerGrid=" + cache +
                '}';
    }

    /**
     * Here the routes between two points are calculated and stored into a hashmap
     * @param centroids
     * @return Hashmap containing all the pre-calculated routes
     */
    private CachedData getRouteMap(ArrayList<MyLatLon> centroids){
        logger.info("Entering method for cache creation");
        long startTime = System.currentTimeMillis();
        HashMap<LatLonCode, RoutePOJO> grid = new HashMap<LatLonCode,RoutePOJO>(centroids.size()*centroids.size());
        for(int i =0;i<centroids.size();i++){
            for(int j=i+1;j<centroids.size();j++){
                MyLatLon source = centroids.get(i);
                MyLatLon target = centroids.get(j);
                RoutePOJO rpj = router.getRoute(source,target);
                LatLonCode data = new LatLonCode(source, target);
                grid.put(data, rpj);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("Created a hashmap of size :" + grid.size());
        double tTime =  (endTime - startTime)/1000.0;
        logger.info("Time taken for creation of cached routes is " + tTime + "seconds");
        CachedData cache = new CachedData(grid,centroids,this.router);
        return cache;
    }

}
