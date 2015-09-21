package dallas;
import grid.BoundingBox;

/**
 * Created by nikki on 5/6/2015.
 * In RouterOpt for dallas
 */
public class BBoxData {

    //Points
    //32.924,-97.092
    //32.473,-97.092
    // 32.924,-96.556
    // 32.473,-96.556
    //bottom left
    public static final float DALLAS_MIN_LAT_OLD =32.166f;
    public  static final float DALLAS_MIN_LAT =32.473f;
    public static final float DALLAS_MIN_LON_OLD= -97.789f;
    public static final float DALLAS_MIN_LON = -97.092f;
    //top right;
    public static final float DALLAS_MAX_LAT_OLD = 33.431f;
    public static final float DALLAS_MAX_LAT= 32.924f;
    public static final float DALLAS_MAX_LON_OLD =-96.113f;
    public static final float DALLAS_MAX_LON = -96.556f;
    //
    public static final float DENVER_MIN_LAT =39.5032f;
    public static final float DENVER_MAX_LAT =39.9529f;
    public static final float DENVER_MIN_LON =-105.2724f;
    public static final float DENVER_MAX_LON =-104.6858f;

    public static BoundingBox getDenverBoundingBox(int cellNum){
        BoundingBox bx =
                new BoundingBox(DENVER_MIN_LAT, DENVER_MIN_LON,DENVER_MAX_LAT, DENVER_MAX_LON,cellNum,cellNum);
        return bx;
    }

    public static BoundingBox getDallasBoundingBox(int cellNum){
        BoundingBox bx =
                new BoundingBox(DALLAS_MIN_LAT, DALLAS_MIN_LON,DALLAS_MAX_LAT, DALLAS_MAX_LON,cellNum,cellNum);
        return bx;
    }


}
