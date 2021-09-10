package org.geosopra;

import javax.xml.crypto.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
    
    public Datapoint[] datumabfrage(Datapoint[] dp, String begin, String end) {
    	
    	int yearBegin = Integer.parseInt(begin.substring(0, 4));
    	int monthBegin = Integer.parseInt(begin.substring(5, 7));
    	int dayBegin = Integer.parseInt(begin.substring(8, 10));
    	
    	int yearEnd = Integer.parseInt(end.substring(0, 4));
    	int monthEnd = Integer.parseInt(end.substring(5, 7));
    	int dayEnd = Integer.parseInt(end.substring(8, 10));
    	
    	long unixBegin = LocalDate.of(yearBegin, monthBegin, dayBegin)
						          .atTime(0, 0, 0)
						          .atZone(ZoneId.of("Europe/Berlin"))
						          .toEpochSecond();
    	long unixEnd = LocalDate.of(yearEnd, monthEnd, dayEnd)
						        .atTime(23, 59, 59)
						        .atZone(ZoneId.of("Europe/Berlin"))
						        .toEpochSecond();
    	
    	System.out.println("Begin: " + unixBegin);
    	System.out.println("End: " + unixEnd);
    	return filter(dp, unixBegin, unixEnd);
    	
    }


}
