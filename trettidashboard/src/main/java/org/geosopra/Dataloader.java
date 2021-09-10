package org.geosopra;

import java.io.*;
import java.util.Scanner;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.util.ResourceUtils;
/**
 * Class to load a CSV Data from resource folder
 * 
 */


public class Dataloader {

    //declaration of path
    static public String fileName = "classpath:routes_bikes_calculated_data.csv";

  

    /**
     * counts the number of rows in the csv file
     * @return count, variable to save the number of csv rows
     * @throws FileNotFoundException
     */
    public int countCSVRows() throws FileNotFoundException {
        File file = ResourceUtils.getFile(fileName);
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\n");
        int count = 0;
        while (sc.hasNext()) // returns a boolean value test
        {
            count++;
            sc.next();
        }
        sc.close();
        return count;
    }

    

     /**
      * format date string in datapoint object and return this
      * @param date
      * @return row, datapoint Object
      * @throws FactoryException
      * @throws TransformException
      * @throws IOException
      */
    public static Datapoint format(String date) throws FactoryException, TransformException, IOException {
        int Start_time_end_index = date.indexOf(';');
        int End_time_right_index = date.indexOf(';', Start_time_end_index + 1);
        int Start_X_right_index = date.indexOf(';', End_time_right_index + 1);
        int Start_Y_right_index = date.indexOf(';', Start_X_right_index + 1);
        int End_X_right_index = date.indexOf(';', Start_Y_right_index + 1);
        int End_Y_right_index = date.indexOf(';', End_X_right_index + 1);
        int distance_right_index = date.indexOf(';', End_Y_right_index + 1);
        int time_in_hours_right_index = date.indexOf(';', distance_right_index + 1);
        int routing_time_right_index = date.indexOf(';', time_in_hours_right_index + 1);

        String starttime = date.substring(0, Start_time_end_index);
        String endtime = date.substring(Start_time_end_index + 1, End_time_right_index);
        String start_x = date.substring(End_time_right_index + 1, Start_X_right_index);
        String start_y = date.substring(Start_X_right_index + 1, Start_Y_right_index);
        String end_X = date.substring(Start_Y_right_index + 1, End_X_right_index);
        String end_Y = date.substring(End_X_right_index + 1, End_Y_right_index);
        String distance = date.substring(End_Y_right_index + 1, distance_right_index);
        String Time_in_Hours = date.substring(distance_right_index + 1, time_in_hours_right_index);
        String routing_time = date.substring(time_in_hours_right_index + 1, routing_time_right_index);
        String routing_distance = date.substring(routing_time_right_index + 1);

        distance = distance.replace(',', '.');
        Time_in_Hours = Time_in_Hours.replace(',', '.');

        Datapoint row = new Datapoint();

        float start_x_long = Float.parseFloat(start_x);
        float start_y_long = Float.parseFloat(start_y);
        float end_x_long = Float.parseFloat(end_X);
        float end_y_long = Float.parseFloat(end_Y);

        row.setDistance(Double.parseDouble(distance));
        row.setEndTime(Long.parseLong(endtime));
        row.setEndX(end_x_long);
        row.setEndY(end_y_long);
        row.setStartTime(Long.parseLong(starttime));
        row.setStartX(start_x_long);
        row.setStartY(start_y_long);
        row.setTime(Double.parseDouble(Time_in_Hours));
        row.setRoutingTime(Double.parseDouble(routing_time));
        row.setRoutingDistance(Double.parseDouble(routing_distance));

        return row;

    }

  

     /**
      * Scan method, for loading CSV Data
      * @param dp_array
      * @param temp
      * @throws Exception
      */
    public static void scan(Datapoint[] dp_array, int temp) throws Exception {
        // parsing a CSV file into Scanner class constructor
        // add personal path in String

        File file = ResourceUtils.getFile(fileName);
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\n"); // sets the delimiter pattern
        boolean routingInfoIncluded = sc.next().contains("ROUTING_TIME;ROUTING_DISTANCE");
        if (!routingInfoIncluded) {
            String fileName = "classpath:routes_bikes.csv";
            overwriteCSV(fileName);
        } else {
            System.out.println("Routing info already included");
            while (sc.hasNext()) // returns a boolean value test
            {
                dp_array[temp] = format(sc.next());
                temp++;

            }
            sc.close(); // closes the scanner
        }
    }



  
    /**
     * writes a new csv file with old data and new routing info
     * @param fileName
     * @throws IOException
     * @throws FactoryException
     * @throws TransformException
     * @throws InterruptedException
     */
    public static void overwriteCSV(String fileName)
            throws IOException, FactoryException, TransformException, InterruptedException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("C:\\Users\\specki\\git\\GISopraProject\\routes_bikes_calculated_data.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File file = ResourceUtils.getFile(fileName);
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\n"); // sets the delimiter pattern

        String columnnames = sc.next();
        StringBuilder builder = new StringBuilder();
        columnnames = columnnames.replace("\n", "").replace("\r", "");
        String columnNamesList = columnnames + ";ROUTING_TIME;ROUTING_DISTANCE";
        builder.append(columnNamesList + "\n");
        int counter = 0;

        while (sc.hasNext()) // goes through all data rows

        {
            //Thread.sleep(5000);
            String row = sc.next();
            row = row.replace("\n", "").replace("\r", "");
            double[] routingInfo = getRoutingInfo(row);
            builder.append(row + ";" + convertToHours(routingInfo[0]) + ";" + convertToKM(routingInfo[1]));
            builder.append("\n");
            System.out.println(counter);
            counter++;
        }

        pw.write(builder.toString());
        pw.close();
        System.out.println("Printing done!");

        sc.close(); // closes the scanner
    }



    /**
     * gets routing info for a row and return it 
     * @param date
     * @return routinginfo as array[][]
     * @throws FactoryException
     * @throws TransformException
     * @throws IOException
     */
    public static double[] getRoutingInfo(String date) throws FactoryException, TransformException, IOException {

        int Start_time_end_index = date.indexOf(';');
        int End_time_right_index = date.indexOf(';', Start_time_end_index + 1);
        int Start_X_right_index = date.indexOf(';', End_time_right_index + 1);
        int Start_Y_right_index = date.indexOf(';', Start_X_right_index + 1);
        int End_X_right_index = date.indexOf(';', Start_Y_right_index + 1);
        int End_Y_right_index = date.indexOf(';', End_X_right_index + 1);

        String start_x = date.substring(End_time_right_index + 1, Start_X_right_index);
        String start_y = date.substring(Start_X_right_index + 1, Start_Y_right_index);
        String end_X = date.substring(Start_Y_right_index + 1, End_X_right_index);
        String end_Y = date.substring(End_X_right_index + 1, End_Y_right_index);

        double start_x_long = Double.parseDouble(start_x);
        double start_y_long = Double.parseDouble(start_y);
        double end_x_long = Double.parseDouble(end_X);
        double end_y_long = Double.parseDouble(end_Y);

        ConvertCoords converter = new ConvertCoords();

        double[] latlonStart = converter.transformUTMToWGS84(start_x_long, start_y_long);
        double[] latlonEnd = converter.transformUTMToWGS84(end_x_long, end_y_long);


        DistanceTime distanceTime = new DistanceTime();

        double[] distim = distanceTime.getDistanceTimeMatrix(latlonStart[0], latlonStart[1], latlonEnd[0],
                latlonEnd[1]);

        System.out.println("API call");
        return new double[] { distim[0], distim[1] };
    }


    /**
     * convert seconds to hours
     * @param seconds
     * @return hour as double
     */
    public static double convertToHours(double seconds) {
        return (seconds / 60) / 60;
    }

    /**
     * convert metres to km
     * @param meters
     * @return km as double
     */
    public static double convertToKM(double meters) {
        return meters / 1000;
    }
}
