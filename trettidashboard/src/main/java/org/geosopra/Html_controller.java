package org.geosopra;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
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

        AnalystIn outlier_detection_min = new OutlierdetectionMin();
        co2_in_g.analyse(dp_array, save_model);

    

    }

    /*
     * Scan method, for loading CSV Data
     * 
     */
    public static void scan(Datapoint[] dp_array, int temp) throws Exception {
        // parsing a CSV file into Scanner class constructor
        // add personal path in String
        String fileName = "classpath:routes_bikes.csv";
        File file = ResourceUtils.getFile(fileName);
        Scanner sc = new Scanner(file);
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

        ConvertCoords converter = new ConvertCoords();

        double[] latlonStart = converter.transformUTMToWGS84(start_x_long, start_y_long);
        double[] latlonEnd = converter.transformUTMToWGS84(end_x_long, end_y_long);

        DistanceTime distanceTime = new DistanceTime();

        // double[] distim = distanceTime.getDistanceTimeMatrix(latlonStart[0],
        // latlonStart[1], latlonEnd[0],
        // latlonEnd[1]);

        double[] distim = new double[] { 66, 66 };
        System.out.println("Duration: " + distim[0]);
        System.out.println("Distance: " + distim[1]);

        row.setRoutingTime(distim[0]);
        row.setRoutingDistance(distim[1]);

        return row;

    }

    @RequestMapping("/")
    public String welcome(Model model) {


        model.addAllAttributes(save_model.asMap());
        model.asMap().forEach((k, e) -> {
            System.out.println(k);
            System.out.println(e);
        });
        return "dashboard";
    }
}
