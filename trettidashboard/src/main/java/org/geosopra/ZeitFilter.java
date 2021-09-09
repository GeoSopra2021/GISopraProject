package org.geosopra;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ZeitFilter {

    public Datapoint[] filter(Datapoint[] dp, double begin, double end){
        List<Datapoint> gefiltert = new ArrayList<>();
        for(int i = 0; i < dp.length; i++) {
            if (dp[i].getStartTime() >= begin && dp[i].getEndTime() <= end){
                gefiltert.add(dp[i]);

            }
        }
        Datapoint[] gefiltertesArray = new Datapoint[gefiltert.size()];
        for(int i = 0; i < gefiltert.size(); i++) {
            gefiltertesArray[i] = gefiltert.get(i);
        }

        return gefiltertesArray;
    }
    
    public long umrechnen(int year, int month, int day) {
    	Date datum = new Date(year, month, day);
    	
    	long ergebnis = datum.getTime();
    	return ergebnis;
    }


}
