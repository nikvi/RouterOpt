package testRoute;

import de.cm.osm2po.model.LatLon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nikki on 3/17/2015.
 * In RouterOpt for testRoute
 */
public class HelperClass {

    public static void getString(File centroids) throws Exception{
        String txt="144.74301249295888";

        String re1="(144\\.74301249295888)";	// Float 1

        Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(txt);
        if (m.find())
        {
            String float1=m.group(1);
            System.out.print("("+float1.toString()+")"+"\n");
        }
        //regex [-+]?[0-9]*\.?[0-9]+

        BufferedReader br = new BufferedReader(new FileReader(centroids));

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            List<LatLon> centLst = new ArrayList<LatLon>();

            while (line != null) {
                sb.append(line);
                System.out.println(line);
                String fREGEXP = "[-+]?([0-9]*\\.[0-9]+|[0-9]+)";
                Pattern pattern = Pattern.compile(fREGEXP, Pattern.DOTALL);
                Matcher matcher = pattern.matcher(line);
                while(matcher.find()) {

                    System.out.println( matcher.group(1));
                }
                //else {
                //  System.out.println("Input does not match pattern.");
                //}
                System.out.println("----------------------------------------");
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
        } finally {
            br.close();
        }
    }


}
