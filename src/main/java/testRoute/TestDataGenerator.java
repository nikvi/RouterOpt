package testRoute;

import data.MyLatLon;
import grid.OSMData;
import java.io.FileWriter;
/**
 * Created by nikki on 4/28/2015.
 * In RouterOpt for testRoute
 */
public class TestDataGenerator {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id,source,destination";
    public static void main(String[] args) {
        String fileName = System.getProperty("user.home")+"\\data.csv";
        System.out.println(fileName);
        writeToFile(fileName,20);
    }
    public static void writeToFile(String fileName,int count){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (int j = 1; j <= count; j++) {
                MyLatLon source = generateSamplePoint();
                MyLatLon target = generateSamplePoint();
                fileWriter.append(String.valueOf(j));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(source.toString());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(target.toString());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            System.out.println("CSV file was created successfully !!!");
            fileWriter.flush();
            fileWriter.close();
        }
        catch(Exception e){
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        }
    }

    private static  MyLatLon generateSamplePoint(){
        double lat1 = Math.random() * (OSMData.maxLat - OSMData.minLat) + OSMData.minLat;
        double lon1 = Math.random() * (OSMData.maxLon - OSMData.minLon) + OSMData.minLon;
        return new MyLatLon(lat1, lon1);
    }
 }
