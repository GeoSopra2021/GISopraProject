package org.geosopra;

import org.springframework.ui.Model;

public abstract class OutlierdetectionMin implements AnalystIn {

	@Override
	public void analyse(Datapoint[] dp, Model model) {
		double ergebnis = getValue(dp[1]);
		for(int i = 0; i < dp.length; i++) {
			if (getValue(dp[i]) < ergebnis) {
				ergebnis = getValue(dp[i]);
			}
		}
		System.out.println("Min: " + ergebnis);
		model.addAttribute(getKey(), ergebnis);
	}

	public  abstract double getValue(Datapoint dp);
	public  abstract String getKey();

	public static class Outlierdetection_Distance_min extends OutlierdetectionMin {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getDistance();
		}
		
		@Override
		public String getKey() {
			return "Distanz_min";
		}

	}

	public static class Outlierdetection_zeit_min extends OutlierdetectionMin {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getTime();
		}
		
		@Override
		public String getKey() {
			return "Zeit_min";
		}

	}

	public static class Outlierdetection_Geschwindigkeit_min extends OutlierdetectionMin {

		@Override
		public double getValue(Datapoint dp) {
			return Math.round((dp.getDistance() / dp.getTime())*100)/100d;
		}
		
		@Override
		public String getKey() {
			return "Geschwindigkeit_min";
		}

	}
}
