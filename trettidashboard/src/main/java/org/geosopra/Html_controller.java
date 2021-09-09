package org.geosopra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Html_controller {

    Model save_model = new ConcurrentModel();

    @PostConstruct
    public void init() {
        Datapoint[] dp_array = new Datapoint[279];
        int temp = 0;

        try {
            scan(dp_array, temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AnalystIn entfernung = new Durchschnitt.DistanzDurchschnitt();
      
        entfernung.analyse(dp_array, save_model);

        Durchschnitt zeit = new Durchschnitt.ZeitDurchschnitt();
        zeit.analyse(dp_array, save_model);

        Durchschnitt Geschwindigkeit = new Durchschnitt.GeschwindigkeitDurchschnitt();
        Geschwindigkeit.analyse(dp_array, save_model);
       
       
        
        Kosten.Tretty tretty = new Kosten.Tretty();
		 tretty.analyse(dp_array, save_model);
		
		Kosten.Lime lime = new Kosten.Lime();
		lime.analyse(dp_array, save_model);
		
		Kosten.Tier tier = new Kosten.Tier();
		tier.analyse(dp_array, save_model);

        AnalystIn co2_in_g = new CO2();
        co2_in_g.analyse(dp_array, save_model);


        //Minimum
        AnalystIn outlier_detection_distance_min = new OutlierdetectionMin.Outlierdetection_Distance_min();
        outlier_detection_distance_min.analyse(dp_array, save_model);

        AnalystIn outlier_detection_time_min = new OutlierdetectionMin.Outlierdetection_zeit_min();
        outlier_detection_time_min.analyse(dp_array, save_model);

        AnalystIn outlier_detection_Geschwindigkeit_min = new OutlierdetectionMin.Outlierdetection_Geschwindigkeit_min();
        outlier_detection_Geschwindigkeit_min.analyse(dp_array, save_model);



        //Maximum
        AnalystIn outlier_detection_distance_max = new OutlierdetectionMax.Outlierdetection_Distance_max();
        outlier_detection_distance_max.analyse(dp_array, save_model);

        AnalystIn outlier_detection_time_max = new OutlierdetectionMax.Outlierdetection_zeit_max();
        outlier_detection_time_max.analyse(dp_array, save_model);

        AnalystIn outlier_detection_Geschwindigkeit_max = new OutlierdetectionMax.Outlierdetection_Geschwindigkeit_max();
        outlier_detection_Geschwindigkeit_max.analyse(dp_array, save_model);

    

    }

    /*
    Scan method, for loading CSV Data

    */
    public static void scan(Datapoint[] dp_array, int temp) throws Exception {
        // parsing a CSV file into Scanner class constructor
        // add personal path in String
        //String  fileName =  "classpath:routes_bikes.csv";
        String  fileName =  "classpath:routes_bikes_calculated_data.csv";
        File file = ResourceUtils.getFile(fileName);
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\n"); // sets the delimiter pattern
        boolean routingInfoIncluded = true;
        //boolean routingInfoIncluded = sc.next().contains("Routing_Time;Routing_Distance");
        if(!routingInfoIncluded){
            overwriteCSV(fileName);
            getRoutingInfo(sc.next());
        }
        else{
            sc.next();
            System.out.println("routing info exists already");
            while (sc.hasNext()) // returns a boolean value test
            {
                dp_array[temp] = format(sc.next());
                temp++;

            }
            sc.close(); // closes the scanner
        }
    }

    public static double convertToHours(double seconds){
        return (seconds/60)/60;
    }

    public static double convertToKM(double  meters){
        return meters/1000;
    }

    /* writes a new csv file with old data and new routing info */
    public static void overwriteCSV(String fileName) throws IOException, FactoryException, TransformException, InterruptedException {
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

        while (sc.hasNext()) // goes through all data rows
        {
            Thread.sleep(5000);
            String row = sc.next();
            row = row.replace("\n", "").replace("\r", "");
            double[] routingInfo = getRoutingInfo(row);
            builder.append(row + ";" + convertToHours(routingInfo[0]) + ";" + convertToKM(routingInfo[1]));
            builder.append("\n");
        }

        pw.write(builder.toString());
        pw.close();
        System.out.println("Printing done!");

        sc.close(); // closes the scanner
    }

    /* gets routing info for a row and return it */
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

        long start_x_long = Long.parseLong(start_x);
        long start_y_long = Long.parseLong(start_y);
        long end_x_long = Long.parseLong(end_X);
        long end_y_long = Long.parseLong(end_Y);

        ConvertCoords converter = new ConvertCoords();

        double[] latlonStart = converter.transformUTMToWGS84(start_x_long, start_y_long);
        double[] latlonEnd = converter.transformUTMToWGS84(end_x_long, end_y_long);

        DistanceTime distanceTime = new DistanceTime();

        double[] distim = distanceTime.getDistanceTimeMatrix(latlonStart[0], latlonStart[1], latlonEnd[0], latlonEnd[1]);

        System.out.println("API call");
        return new double[]{distim[0], distim[1]};
    }

    /*
     * format date string in datapoint object and return this
     * 
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
        row.setRoutingTime(Double.parseDouble(routing_time));
        row.setRoutingDistance(Double.parseDouble(routing_distance));

        return row;

    }

    /*@RequestMapping("/")
    public String dashboard(Model model) {


        model.addAllAttributes(save_model.asMap());
        model.asMap().forEach((k, e) -> {
            System.out.println(k);
            System.out.println(e);
        });
        
        return "dashboard";
    }*/

    @RequestMapping("/")
    public String dashboard_with_filter(
        @RequestParam(required = false, value= "trip-start") String start,
        @RequestParam(required = false, value ="trip-end") String end, 
        Model model
    ) {
        System.out.println("start");
        System.out.println(start);
        System.out.println("end");
        System.out.println(end);

        model.addAllAttributes(save_model.asMap());
        model.asMap().forEach((k, e) -> {
            System.out.println(k);
            System.out.println(e);
        });
        return "dashboard";
    }
}
