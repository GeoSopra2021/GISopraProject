package org.geosopra;



import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
public class Html_controller {

    


    int csvRowNumber = getCSVRowNumber();
    Datapoint[] rohdaten_array = new Datapoint[csvRowNumber-1];



    AnalystIn entfernung = new Durchschnitt.DistanzDurchschnitt();
    Durchschnitt zeit = new Durchschnitt.ZeitDurchschnitt();
    Durchschnitt Geschwindigkeit = new Durchschnitt.GeschwindigkeitDurchschnitt();
    Kosten.Lime lime = new Kosten.Lime();
    Kosten.Tretty tretty = new Kosten.Tretty();
    Kosten.Tier tier = new Kosten.Tier();
    AnalystIn co2_in_g = new CO2();

    AnalystIn outlier_detection_distance_min = new OutlierdetectionMin.Outlierdetection_Distance_min();
    AnalystIn outlier_detection_time_min = new OutlierdetectionMin.Outlierdetection_zeit_min();
    AnalystIn outlier_detection_Geschwindigkeit_min = new OutlierdetectionMin.Outlierdetection_Geschwindigkeit_min();
    AnalystIn outlier_detection_distance_max = new OutlierdetectionMax.Outlierdetection_Distance_max();
    AnalystIn outlier_detection_time_max = new OutlierdetectionMax.Outlierdetection_zeit_max();
    AnalystIn outlier_detection_Geschwindigkeit_max = new OutlierdetectionMax.Outlierdetection_Geschwindigkeit_max();

    public Html_controller() throws IOException {
    }

    public int getCSVRowNumber() throws IOException {
        Dataloader dataloader = new Dataloader();
        return dataloader.countCSVRows();
    }

    @PostConstruct
    public void init() {

        int temp = 0;

        try {
          Dataloader.scan(rohdaten_array, temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    


    

    

    
    /*
     * @RequestMapping("/") public String dashboard(Model model) {
     * 
     * 
     * model.addAllAttributes(save_model.asMap()); model.asMap().forEach((k, e) -> {
     * System.out.println(k); System.out.println(e); });
     * 
     * return "dashboard"; }
     */

    @RequestMapping("/")
    public String dashboard_with_filter(@RequestParam(required = false, value = "trip-start", defaultValue="2021-01-01") String start,
            @RequestParam(required = false, value = "trip-end", defaultValue="2099-04-20") String end, Model model) {
        System.out.println("start");
        System.out.println(start);
        System.out.println("end");
        System.out.println(end);

        model.addAttribute("start_time", start);
        model.addAttribute("end_time", end);
        

        System.out.println("rohdaten.length");
        System.out.println(rohdaten_array.length);

        System.out.println(rohdaten_array[rohdaten_array.length-2].getDistance());
        for (int i = 0; i < rohdaten_array.length; i++) {

            if (rohdaten_array[i] == null) {
                System.out.println(i);
            }
          
        }

        ZeitFilter filtern = new ZeitFilter();
        Datapoint[] gefiltertes_array = filtern.datumabfrage(rohdaten_array, start, end);
       

        if (gefiltertes_array.length == 0) {
            model.addAttribute("exist_data", true);
            return "dashboard";
        } 

        entfernung.analyse(gefiltertes_array, model);
        zeit.analyse(gefiltertes_array, model);
        Geschwindigkeit.analyse(gefiltertes_array, model);
        tretty.analyse(gefiltertes_array, model);
        lime.analyse(gefiltertes_array, model);
        tier.analyse(gefiltertes_array, model);
        co2_in_g.analyse(gefiltertes_array, model);

        // Minimum
        outlier_detection_distance_min.analyse(gefiltertes_array, model);
        outlier_detection_time_min.analyse(gefiltertes_array, model);
        outlier_detection_Geschwindigkeit_min.analyse(gefiltertes_array, model);

        // Maximum
        outlier_detection_distance_max.analyse(gefiltertes_array,  model);
        outlier_detection_time_max.analyse(gefiltertes_array,  model);
        outlier_detection_Geschwindigkeit_max.analyse(gefiltertes_array, model);



        model.addAllAttributes(model.asMap());

        return "dashboard";
    }
}
