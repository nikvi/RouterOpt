package dallas;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Created by nikki on 6/3/2015.
 * In RouterOpt for dallas
 */
public class ReadInput {
    public static void main(String[] args)throws Exception{
        InputStream file = new FileInputStream("queries.ser");
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream (buffer);
        List<RandomGenerator.QueryLatLon> recoveredQueries = (List<RandomGenerator.QueryLatLon>)input.readObject();
        System.out.println(recoveredQueries.size());

        //for(RandomGenerator.QueryLatLon qr : recoveredQueries){
           // System.out.println(qr);
       // }
    }
}
