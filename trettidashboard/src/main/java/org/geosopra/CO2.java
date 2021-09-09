package org.geosopra;

import org.springframework.ui.Model;
public class CO2 implements AnalystIn {

    @Override
    public void analyse(Datapoint[] dp, Model model){
        Durchschnitt.DistanzDurchschnitt distanz = new Durchschnitt.DistanzDurchschnitt();

         double ds = distanz.analyseIntern(dp);
         double car = Math.round(257 * ds * 100.0) / 100.0;
         double scooter = Math.round(126 * ds * 100.0) / 100.0;
         double bicycle = Math.round(5 * ds * 100.0) / 100.0;
         System.out.println("Der CO2-Ausstoß eines Autos auf der Strecke" +
                 "beträgt "+ car +" g, der des E-Scooters " + scooter +
                 " g und der für ein Tretty-Fahrrad " + bicycle + " g.");
         System.out.println("Auf der Fahrt mit einem Tretty-Fahrrad wird" +
                 ersparnis(car,bicycle)+ " Gramm CO2 im Vergleich zu einem" +
                 "Auto gespart.");
        System.out.println("Auf der Fahrt mit einem Tretty-Fahrrad wird" +
                ersparnis(scooter,bicycle)+ " Gramm CO2 im Vergleich zu einem" +
                "E-Scooter gespart.");

        model.addAttribute("CO2_Car", car);
        model.addAttribute("CO2_Scooter", scooter);
        model.addAttribute("CO2_Bicycle", bicycle);
        model.addAttribute("CO2_saving_Car_Tretty", (car-bicycle));
        model.addAttribute("CO2_saving_Scooter_Tretty", (scooter-bicycle));
        }
    


    /**
     * CO2 Ersparnis eines Tretty-Fahrrads gegenüber der selben Durchschnittsstrecke
     * mit einem Auto bzw E-Scooter.
     */

    public double ersparnis(double a, double b){
        return a-b;
         // double ersparnisCar = double car - double bicycle;
         //double ersparnisScooter = double scooter - double bicycle;

    }



    @Override
    public double getValue(Datapoint dp) {
        // TODO Auto-generated method stub
        return 0;
    }

}
