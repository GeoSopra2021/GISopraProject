package org.geosopra;

public abstract class CO2 implements AnalystIn {

    @Override
    public double analyse(Datapoint[] dp){
        Durchschnitt.DistanzDurchschnitt distanz = new Durchschnitt.DistanzDurchschnitt();

         double ds = distanz.analyse(dp);
         double car = 126 * ds;
         double scooter = 126 * ds;
         double bicycle = 5 * ds;
         System.out.println("Der CO2-Ausstoß eines Autos auf der Strecke" +
                 "beträgt "+ car +" g, der des E-Scooters" + scooter +
                 " g und der für ein Tretty-Fahrrad " + bicycle + " g.");
         System.out.println("Auf der Fahrt mit einem Tretty-Fahrrad wird" +
                 ersparnis(car,bicycle)+ " Gramm CO2 im Vergleich zu einem" +
                 "Auto gespart.");
        System.out.println("Auf der Fahrt mit einem Tretty-Fahrrad wird" +
                ersparnis(scooter,bicycle)+ " Gramm CO2 im Vergleich zu einem" +
                "E-Scooter gespart.");
                return ds;
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

}
