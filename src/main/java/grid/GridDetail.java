package grid;

import data.MyLatLon;

import java.util.List;

/**
 * Created by nikki on 3/19/2015.
 * In RouterOpt for grid
 */
public class GridDetail {
     int numColumns;
     int numRows;
    double cellHeight;
    double cellWidth;
    //number of points should ideally be numCols-1
    List<MyLatLon> gridPointsNorth;
    //numPoints should be rowNums-1
    List<MyLatLon> gridPointsSouth;

    public GridDetail(int numColumns, int numRows) {
        this.numColumns = numColumns;
        this.numRows = numRows;
    }

    public GridDetail(int numColumns, int numRows, double cellHeight, double cellWidth) {
        this.numColumns = numColumns;
        this.numRows = numRows;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
    }

    @Override
    public String toString() {
        return "GridDetail{" +
                "numColumns=" + numColumns +
                ", numRows=" + numRows +
                ", cellHeight=" + cellHeight +
                ", cellWidth=" + cellWidth +
                ", gridPointsNorth=" + gridPointsNorth +
                ", getGridPointsSouth=" + gridPointsSouth +
                '}';
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(double cellHeight) {
        this.cellHeight = cellHeight;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    //gets the number of points along the boundary of the cell so easier to get the cooridinates of all the cells.
    public List<MyLatLon> getGridPointsNorth() {
        return gridPointsNorth;
    }

    public void setGridPointsNorth(List<MyLatLon> gridPointsNorth) {
        this.gridPointsNorth = gridPointsNorth;
    }

    public List<MyLatLon> getGridPointsSouth() {
        return gridPointsSouth;
    }

    public void setGridPointsSouth(List<MyLatLon> gridPointsSouth) {
        this.gridPointsSouth = gridPointsSouth;
    }
}
