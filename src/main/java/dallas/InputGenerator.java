package dallas;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Created by nikki on 6/3/2015.
 * In RouterOpt for dallas
 */
public class InputGenerator {
    private static final Logger fLogger =
            Logger.getLogger(InputGenerator.class.getPackage().getName());

    public static void main(String[] args) throws IOException {
        File graphFile = new File(args[0]);
        RandomGenerator generator = new RandomGenerator(3000,BBoxData.DENVER_MIN_LAT,BBoxData.DENVER_MAX_LAT,BBoxData.DENVER_MIN_LON,BBoxData.DENVER_MAX_LON,graphFile);
        ArrayList<RandomGenerator.QueryLatLon> randomPoints = generator.getRandData();
        //serialize the List
        OutputStream file = new FileOutputStream("queries.ser");
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(randomPoints);
        output.flush();
        output.close();
        buffer.close();
        file.close();
    }
}
