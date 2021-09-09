package org.geosopra;

import org.springframework.ui.Model;

public abstract class OutlierdetectionMax implements AnalystIn {
	
	@Override
	public void analyse(Datapoint[] dp, Model model) {
		double ergebnis = getValue(dp[1]);
		for(int i = 0; i < dp.length; i++) {
			if (getValue(dp[i]) > ergebnis) {
				ergebnis = getValue(dp[i]);
			}
		}
		model.addAttribute(getKey(), ergebnis);
	}

	public abstract double getValue(Datapoint dp);
	public abstract String getKey();
	
	public static class Outlierdetection_Distance_max extends OutlierdetectionMax {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getRoutingDistance();
		}

		@Override
		public String getKey() {
			return "Distanz_max";
		}

	}
	
	public static class Outlierdetection_zeit_max extends OutlierdetectionMax {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getRoutingTime();
		}
		
		@Override
		public String getKey() {
			return "Zeit_max";
		}

	}
	
	public static class Outlierdetection_Geschwindigkeit_max extends OutlierdetectionMax {

		@Override
		public double getValue(Datapoint dp) {
			return Math.round((dp.getRoutingDistance() / dp.getRoutingTime())*100)/100d;
		}
		
		@Override
		public String getKey() {
			return "Geschwindigkeit_max";
		}

	}
	
}
