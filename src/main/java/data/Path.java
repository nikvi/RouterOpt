package data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nikki on 6/2/2015.
 * In RouterOpt for data
 */
public class Path {
    private float length;
    private List<Integer> pathIds;
    private boolean calculated;


    public boolean isCalculated() {
        return calculated;
    }

    public void setCalculated(boolean isCalc) {
        this.calculated = isCalc;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public List<Integer> getPathIds() {
        return pathIds;
    }

    public void setPathIds(LinkedList<Integer> pathIds) {
        this.pathIds = pathIds;
    }

    public Path(){
        this.length =0.0f;
        this.pathIds = null;
        calculated = Boolean.FALSE;
    }
    public Path(LinkedList<Integer> data,Float distance){
        this.length = distance;
        this.pathIds = data;
        calculated = Boolean.FALSE;
    }

}
