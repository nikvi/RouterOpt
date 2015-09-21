package data;
import de.cm.osm2po.model.LatLon;
import de.cm.osm2po.primitives.InStream;
import de.cm.osm2po.primitives.OutStream;
import de.cm.osm2po.primitives.Var;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
public class MyLatLon implements Var,Serializable {
    public static final long NULL_COORDINATE = -9223372036854775808L;
    private static final long LONG_LOW_INT_MASK = 4294967295L;
    private static final long LONG_HIGH_INT_MASK = -4294967296L;
    protected long coord;
    private double eucDistance;

    public double getEuDistance() {
        return eucDistance;
    }

    public void setEuDistance(double euDistance) {
        this.eucDistance = euDistance;
    }

    public MyLatLon() {
        this.coord = -9223372036854775808L;
    }

    public MyLatLon(double lat, double lon) {
        this.setLatLon(lat, lon);
    }

    public MyLatLon(double lat,double lon,double eucDistance) {
        this.eucDistance = eucDistance;
        this.setLatLon(lat,lon);
    }

    public MyLatLon(LatLon latlon) {
        this.setLatLon(latlon.getLat(), latlon.getLon());
    }


    public MyLatLon copy() {
        MyLatLon copy = new MyLatLon();
        copy.coord = this.coord;
        return copy;
    }

    public long getCoord() {
        return this.coord;
    }

    public MyLatLon setCoord(long coord) {
        this.coord = coord;
        return this;
    }

    public void setLatLon(double lat, double lon) {
        long llat = (long)StrictMath.floor(lat * 1.0E7D + 0.5D);
        long llon = (long)StrictMath.floor(lon * 1.0E7D + 0.5D);
        this.coord = llat << 32 | llon & 4294967295L;
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof MyLatLon)) {
            return false;
        } else {
            MyLatLon that = (MyLatLon)obj;
            return this.coord == that.coord;
        }
    }

    @Override
    public int hashCode() {
        return (int) (coord ^ (coord >>> 32));
    }

    public void setLat(double lat) {
        this.coord &= 4294967295L;
        long llat = (long)StrictMath.floor(lat * 1.0E7D + 0.5D);
        this.coord |= llat << 32;
    }

    public static double getLat(long latLon) {
        int ilat = (int)(latLon >> 32);
        return (double)ilat / 1.0E7D;
    }

    @XmlElement
    public double getLat() {
        int ilat = (int)(this.coord >> 32);
        return (double)ilat / 1.0E7D;
    }

    public void setLon(double lon) {
        this.coord &= -4294967296L;
        long llon = (long)StrictMath.floor(lon * 1.0E7D + 0.5D);
        this.coord |= llon & 4294967295L;
    }

    public static double getLon(long latLon) {
        int ilon = (int)(latLon & 4294967295L);
        return (double)ilon / 1.0E7D;
    }

    @XmlElement
    public double getLon() {
        int ilon = (int)(this.coord & 4294967295L);
        return (double)ilon / 1.0E7D;
    }

    public Var readFromStream(InStream inStream) {
        this.coord = inStream.readLong();
        return this;
    }

    public void writeToStream(OutStream outStream) {
        outStream.writeLong(this.coord);
    }

    public String toString() {
        return -9223372036854775808L == this.coord?"null":"(" + this.getLat() + " " + this.getLon() + ")";
    }
}
