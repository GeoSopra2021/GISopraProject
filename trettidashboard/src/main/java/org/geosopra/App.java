package org.geosopra;

import java.io.*;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world! test
 *
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {

    	SpringApplication.run(App.class, args);
     


        Datapoint[] dp_array = new Datapoint[279];
        int temp = 0;
        scan(dp_array, temp);
        System.out.println("test" + dp_array[5].getDistance());

        AnalystIn entfernung = new Durchschnitt.DistanzDurchschnitt();
        double ergebnis = entfernung.analyse(dp_array);
        System.out.println("Die Durchschnittslänge ist " + ergebnis + " km");
        
        AnalystIn zeit = new Durchschnitt.ZeitDurchschnitt();
        ergebnis = zeit.analyse(dp_array);
        System.out.println("Die Durchschnittszeit ist " + ergebnis * 100 + " min");
        
        Kosten.Tretty tretty = new Kosten.Tretty();
		System.out.println("Kosten Tretty: " + tretty.analyse(dp_array));
		
		Kosten.Lime lime = new Kosten.Lime();
		System.out.println("Kosten Lime: " + lime.analyse(dp_array));
		
		Kosten.Tier tier = new Kosten.Tier();
		System.out.println("Kosten Tier: " + tier.analyse(dp_array));

    }


    /*
    Scan method, for loading CSV Data

    */
    public static void scan(Datapoint[] dp_array, int temp) throws Exception {
        // parsing a CSV file into Scanner class constructor
        // add personal path in String
        Scanner sc = new Scanner(new File(
                "D:\\Dokumente\\GISopraProject\\routes_bikes.csv"));
        sc.useDelimiter("\n"); // sets the delimiter pattern
        sc.next();
        while (sc.hasNext()) // returns a boolean value test
        {

            dp_array[temp] = format(sc.next());
            temp++;

        }
        sc.close(); // closes the scanner
    }


    /*
    format date string in datapoint object and return this

    */
    public static Datapoint format(String date) {
        int Start_time_end_index = date.indexOf(';');
        int End_time_right_index = date.indexOf(';', Start_time_end_index + 1);
        int Start_X_right_index = date.indexOf(';', End_time_right_index + 1);
        int Start_Y_right_index = date.indexOf(';', Start_X_right_index + 1);
        int End_X_right_index = date.indexOf(';', Start_Y_right_index + 1);
        int End_Y_right_index = date.indexOf(';', End_X_right_index + 1);
        int distance_right_index = date.indexOf(';', End_Y_right_index + 1);

        String starttime = date.substring(0, Start_time_end_index);
        String endtime = date.substring(Start_time_end_index + 1, End_time_right_index);
        String start_x = date.substring(End_time_right_index + 1, Start_X_right_index);
        String start_y = date.substring(Start_X_right_index + 1, Start_Y_right_index);
        String end_X = date.substring(Start_Y_right_index + 1, End_X_right_index);
        String end_Y = date.substring(End_X_right_index + 1, End_Y_right_index);
        String distance = date.substring(End_Y_right_index + 1, distance_right_index);
        String Time_in_Hours = date.substring(distance_right_index + 1);

        distance = distance.replace(',', '.');
        Time_in_Hours = Time_in_Hours.replace(',', '.');

        Datapoint row = new Datapoint();

        row.setDistance(Double.parseDouble(distance));
        row.setEndTime(Long.parseLong(endtime));
        row.setEndX(Long.parseLong(end_X));
        row.setEndY(Long.parseLong(end_Y));
        row.setStartTime(Long.parseLong(starttime));
        row.setStartX(Long.parseLong(start_x));
        row.setStartY(Long.parseLong(start_y));
        row.setTime(Double.parseDouble(Time_in_Hours));

        return row;

    }

    public static void create_array(Datapoint row) {
    }

}

