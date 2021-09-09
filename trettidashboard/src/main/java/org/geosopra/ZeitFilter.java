package org.geosopra;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ZeitFilter {

    public Datapoint[] filter(Datapoint[] dp, long begin, long end){
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
    	
    	long unixTime = datum.getTime();
    	return unixTime;
    }
    
    public Datapoint[] datumabfrage(Datapoint[] dp, String begin, String end) {
    	
    	int yearBegin = Integer.parseInt(begin.substring(0, 3));
    	int monthBegin = Integer.parseInt(begin.substring(5, 6));
    	int dayBegin = Integer.parseInt(begin.substring(8, 9));
    	
    	int yearEnd = Integer.parseInt(begin.substring(0, 3));
    	int monthEnd = Integer.parseInt(begin.substring(5, 6));
    	int dayEnd = Integer.parseInt(begin.substring(8, 9));
    	
    	long unixBegin = umrechnen(yearBegin, monthBegin, dayBegin);
    	long unixEnd = umrechnen(yearEnd, monthEnd, dayEnd);
    	
    	return filter(dp, unixBegin, unixEnd);
    	
    }


}
