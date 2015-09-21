package algoTest;

import dallas.RandomGenerator;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by nikki on 6/3/2015.
 * In RouterOpt for algoTest
 */
public class CacheTest {
    private static final Logger logger =
            Logger.getLogger(CacheTest.class.getName());
    public static void main(String[] args) throws Exception{
        String graphFile = args[0];
        int cellCount = 25;
        String fileName = System.getProperty("user.home")+"\\researchData\\try" +
                "\\25x25" +".csv";
        InputStream file = new FileInputStream("queries.ser");
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        List<RandomGenerator.QueryLatLon> recoveredQueries = (List<RandomGenerator.QueryLatLon>)input.readObject();
        OutputCached.writeToFile(recoveredQueries,graphFile,fileName,cellCount);

    }
}
