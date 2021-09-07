package org.geosopra;

import java.io.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */

public class App {
    public static void main(String[] args) throws Exception {

        Datapoint[] dp = new Datapoint[6];
        dp[0] = new Datapoint();
        dp[0].setDistance(3);
        dp[1] = new Datapoint();
        dp[1].setDistance(2);
        dp[2] = new Datapoint();
        dp[2].setDistance(2);
        dp[3] = new Datapoint();
        dp[3].setDistance(1);
        dp[4] = new Datapoint();
        dp[4].setDistance(4);
        dp[5] = new Datapoint();
        dp[5].setDistance(6);
        AnalystIn entfernung = new Durchschnitt.DistanzDurchschnitt();
        double ergebnis = entfernung.analyse(dp);
        System.out.println("Die Durchschnittslänge ist " +ergebnis + " km");
        
        //Datapoint[] dp_array = new Datapoint[278];
        // parsing a CSV file into Scanner class constructor
        // add personal path in String
        Scanner sc = new Scanner(new File(
                "C:\\Users\\Timo\\OneDrive - Universität Münster (1)\\6. Fachsemester_SS_2021\\GISopraProject\\routes_bikes.csv"));
        sc.useDelimiter(","); // sets the delimiter pattern
        while (sc.hasNext()) // returns a boolean value
        {
            format(sc.next());
            System.out.print(sc.next()); // find and returns the next complete token from this scanner
        }
        sc.close(); // closes the scanner
    }

    public static void format(String date) {
        int Start_time_end_index = date.indexOf(';');
        int End_time_right_index = date.indexOf(';', Start_time_end_index);
        int Start_X_right_index = date.indexOf(';',End_time_right_index);
        int Start_Y_right_index = date.indexOf(';', Start_X_right_index);
        int End_X_right_index = date.indexOf(';', Start_Y_right_index);
        int End_Y_right_index = date.indexOf(';', End_X_right_index);
        int distance_right_index = date.indexOf(';', End_Y_right_index);
       


        String starttime = date.substring(0, Start_time_end_index);
        String endtime = date.substring(Start_time_end_index, End_time_right_index);
        String start_x = date.substring(End_time_right_index, Start_X_right_index);
        String start_y = date.substring(Start_X_right_index, Start_Y_right_index);
        String end_X = date.substring(Start_Y_right_index, End_X_right_index);
        String end_Y = date.substring(End_X_right_index, End_Y_right_index);
        String distance = date.substring(End_Y_right_index);

        Datapoint row =new Datapoint();
        row.setDistance(Integer.parseInt(distance));
        row.setEndTime(Integer.parseInt(endtime));
        row.setEndX(Integer.parseInt(end_X));
        row.setEndY(Integer.parseInt(end_Y));
        row.setStartTime(Integer.parseInt(starttime));
        row.setStartX(Integer.parseInt(start_x));
        row.setStartY(Integer.parseInt(start_y));
        row.setTime(Integer.parseInt(endtime)-Integer.parseInt(starttime));

        System.out.println(row);

    }
}
