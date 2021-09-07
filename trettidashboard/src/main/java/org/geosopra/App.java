package org.geosopra;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
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

    }
}
