package grid;

import data.MyLatLon;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by nikki on 3/18/2015.
 * In RouterOpt for PACKAGE_NAME
 */
public class BoundingBox {
    //maxlatLon
    private MyLatLon northEast;
    private MyLatLon northWest;
    private MyLatLon southEast;
    // minLatlon
    private MyLatLon southWest;

    private GridDetail grd;

    private void setLatLons(double minLat,double minLon,double maxLat,double maxLon){
        this.southWest = new MyLatLon(minLat,minLon);
        this.southEast = new MyLatLon(minLat,maxLon);
        this.northEast = new MyLatLon(maxLat,maxLon);
        this.northWest = new MyLatLon(maxLat,minLon);
    }

    public BoundingBox(){
        this.setLatLons(OSMData.minLat,OSMData.minLon, OSMData.maxLat,OSMData.maxLon);
    }

    public BoundingBox(int cellNumbers){
        this();
        int numColumns = (int)Math.ceil(Math.sqrt(cellNumbers));
        int numRows =(int) Math.ceil(cellNumbers / numColumns);
        this.grd = getGridDetail(numRows,numColumns);
    }

    public BoundingBox( double minLat,double minLon,double maxLat,double maxLon, int rows, int columns){
        this.setLatLons(minLat,minLon,maxLat,maxLon);
        this.grd = getGridDetail(rows,columns);
    }
    public BoundingBox(int rows, int columns){
        this();
        this.grd = getGridDetail(rows,columns);
    }


    private static final Logger logger =
            Logger.getLogger(BoundingBox.class.getName());

    public MyLatLon getNorthEast() {
        return northEast;
    }

    public void setNorthEast(MyLatLon northEast) {
        this.northEast = northEast;
    }

    public MyLatLon getNorthWest() {
        return northWest;
    }

    public void setNorthWest(MyLatLon northWest) {
        this.northWest = northWest;
    }

    public MyLatLon getSouthEast() {
        return southEast;
    }

    public void setSouthEast(MyLatLon southEast) {
        this.southEast = southEast;
    }

    public MyLatLon getSouthWest() {
        return southWest;
    }

    public void setSouthWest(MyLatLon southWest) {
        this.southWest = southWest;
    }

    public GridDetail getGrd() {
        return grd;
    }

    public void setGrd(GridDetail grd) {
        this.grd = grd;
    }

    public MyLatLon getCentroid(){
        //(max value of polygon and min value)
        double  centroidLat = this.southWest.getLat() + ((this.northEast.getLat() -this.southWest.getLat())/2);
        double centroidLon = this.southWest.getLon() + ((this.northEast.getLon() -this.southWest.getLon())/2);
        MyLatLon centroid = new MyLatLon(centroidLat,centroidLon);
        return centroid;
    }
    //divide the bounding box into a number of grid cells or boxs as specified and the return the centroids of each Box
    public ArrayList<MyLatLon> getCentroids(){
       List<Cell>  gridCells = this.getGridCells();
         ArrayList centroids = new ArrayList<MyLatLon>();
        for(Cell cell: gridCells){
            centroids.add(new MyLatLon(cell.getCentroid().getLat(),cell.getCentroid().getLon()));
        }
        logger.info("Numbers of centroids is "+centroids.size());
        logger.info("All centroid of the cells are"+ centroids.toString());
        return centroids;
    }

    private double getHeight(){
        double height = GeoFunctions.getDistanceMeters(northEast,southEast);
        return height;
    }

    private GridDetail getGridDetail(int numRows,int numColumns){
        logger.info("Logger Name: " + logger.getName());
        double bboxHeight =getHeight();
        double bboxWidth = getWidth();
        logger.info("Grid created: "+numRows+"x"+numColumns);
        logger.info("Width of the bounding box is: "+bboxWidth/1000 + "kms");
        logger.info("Height of the bounding box is: "+ bboxHeight/1000+ "kms");
        double cellHeight= bboxHeight/numRows;
        double cellWidth= bboxWidth/numColumns;
        logger.info("Width of a cell is: "+ cellWidth/1000);
        logger.info("Hieght of a cell is:"+ cellHeight/1000);
        GridDetail grd = new GridDetail(numColumns,numRows,cellHeight,cellWidth);
        List<MyLatLon> widthPoints = GeoFunctions.getPointsOnLine((numColumns - 1), this.getNorthWest(), cellWidth, (360 - getWBearing()));
        widthPoints.add(this.getNorthEast());
        List<MyLatLon> heightPoints = GeoFunctions.getPointsOnLine((numRows - 1), this.getNorthWest(), cellHeight, getHBearing());
        heightPoints.add(this.getSouthWest());
        grd.setGridPointsNorth(widthPoints);
        grd.setGridPointsSouth(heightPoints);
        return grd;
    }


    public List<Cell> getGridCells(){
        List<Cell> gridCells = new ArrayList<Cell>();
        List<MyLatLon> width = this.getGrd().getGridPointsNorth();
        List<MyLatLon> height = this.getGrd().getGridPointsSouth();
        for(int i=0;i<this.grd.getNumRows();i++) {
            MyLatLon minLat = height.get(i);
            for (int j = 0; j < this.grd.numColumns; j++) {
                MyLatLon maxLat;
                if(i==0){
                    maxLat =width.get(j);
                }
                else{
                    maxLat = new MyLatLon(height.get((i-1)).getLat(),width.get(j).getLon());
                }
                Cell lt = new Cell(minLat,maxLat);
                minLat = new MyLatLon(height.get(i).getLat(), width.get(j).getLon());
                gridCells.add(lt);
            }
        }
        return gridCells;

    }


    //bearing while going towards east from west
    double getWBearing(){
        return  GeoFunctions.getBearing(this.northWest,this.northEast);
    }

    //bearing moving from top to bottom;
    double getHBearing(){
        return GeoFunctions.getBearing(this.northWest,this.southWest);
    }

    // list of points from grid

    private double getWidth(){
        double width = GeoFunctions.getDistanceMeters(northWest,northEast);
        return width;
    }


    @Override
    public String toString() {
        return "BoundingBox{" +
                "northEast=" + northEast +
                ", northWest=" + northWest +
                ", southEast=" + southEast +
                ", southWest=" + southWest +
                '}';
    }
}
