package algoTest;

import dallas.RandomGenerator;

import java.io.*;
import java.util.List;

/**
 * Created by nikki on 6/3/2015.
 * In RouterOpt for algoTest
 */
public class DijkstraTest {
    public static void main(String[] args) throws Exception{
        File graphFile = new File(args[0]);
        String fileName = System.getProperty("user.home")+"\\researchData\\try" +
                "\\dijkstra.csv";
        InputStream file = new FileInputStream("queries.ser");
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        List<RandomGenerator.QueryLatLon> recoveredQueries = (List<RandomGenerator.QueryLatLon>)input.readObject();
        Output.writeToFile(recoveredQueries,graphFile,fileName);

    }
}
