package org.geosopra;

import java.io.*;
import java.util.Scanner;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

/**
 * Hello world! test
 *
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {

    	SpringApplication.run(App.class, args);
        
        /*
        Datapoint[] dp_array = new Datapoint[279];
        int temp = 0;
        scan(dp_array, temp);

        System.out.println("test" + dp_array[5].getDistance());

        Durchschnitt entfernung = new Durchschnitt.DistanzDurchschnitt();
        double ergebnis = entfernung.analyseIntern(dp_array);
        System.out.println("Die Durchschnittslänge ist " + ergebnis + " km");
        
        Durchschnitt zeit = new Durchschnitt.ZeitDurchschnitt();
        ergebnis = zeit.analyseIntern(dp_array);
        System.out.println("Die Durchschnittszeit ist " + ergebnis * 100 + " min");
        
        Kosten.Tretty tretty = new Kosten.Tretty();
		System.out.println("Kosten Tretty: " + tretty.analyseIntern(dp_array));
		
		Kosten.Lime lime = new Kosten.Lime();
		System.out.println("Kosten Lime: " + lime.analyseIntern(dp_array));
		
		Kosten.Tier tier = new Kosten.Tier();
		System.out.println("Kosten Tier: " + tier.analyseIntern(dp_array));
       */

    }





    /*
    format date string in datapoint object and return this

    */
    public static Datapoint format(String date) throws FactoryException, TransformException, IOException, InterruptedException {



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

        long start_x_long = Long.parseLong(start_x);
        long start_y_long = Long.parseLong(start_y);
        long end_x_long = Long.parseLong(end_X);
        long end_y_long = Long.parseLong(end_Y);

        row.setDistance(Double.parseDouble(distance));
        row.setEndTime(Long.parseLong(endtime));
        row.setEndX(end_x_long);
        row.setEndY(end_y_long);
        row.setStartTime(Long.parseLong(starttime));
        row.setStartX(start_x_long);
        row.setStartY(start_y_long);
        row.setTime(Double.parseDouble(Time_in_Hours));



        return row;

    }

}

