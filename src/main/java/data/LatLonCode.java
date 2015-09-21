package data;

import java.util.Comparator;

/**
 * Created by nikki on 6/5/2015.
 * In RouterOpt for data
 */
public class LatLonCode implements Comparable<LatLonCode> {
    MyLatLon sourceCentroid;
    MyLatLon targetCentroid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatLonCode)) return false;

        LatLonCode that = (LatLonCode) o;
        if (this.sourceCentroid.coord == that.sourceCentroid.coord &&
                this.targetCentroid.coord == that.targetCentroid.coord){
            return true;
        } else {
            return false;
        }
    }
    public String toString() {
        return -9223372036854775808L == this.sourceCentroid.coord?"null":"(" + this.sourceCentroid.coord + " " + this.targetCentroid.coord + ")";
    }

    @Override
    public int compareTo(LatLonCode o1) {
        long src = this.sourceCentroid.coord;
        long trg = this.targetCentroid.coord;
        long otherSrc = o1.sourceCentroid.coord;
        long otherTrg = o1.targetCentroid.coord;
        if (src > otherSrc || trg > otherTrg) {
            return 1;
        }
        else if(src==otherSrc && trg==otherTrg){
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int hashCode() {
        int hash = 23;
        hash = ((hash << 5) * 37) ^ (sourceCentroid == null ? 0 : sourceCentroid.hashCode());
        hash = ((hash << 5) * 37) ^ (targetCentroid == null ? 0 : targetCentroid.hashCode());
        return hash;
    }


    public LatLonCode(MyLatLon source,MyLatLon target){
        this.sourceCentroid = source;
        this.targetCentroid = target;
    }

}
