package org.geosopra;

import org.springframework.ui.Model;

public abstract class OutlierdetectionMax implements AnalystIn {
	
	@Override
	public void analyse(Datapoint[] dp, Model model) {
		double ergebnis = 0;
		for(int i = 0; i < dp.length; i++) {
			if (getValue(dp[i]) > ergebnis) {
				ergebnis = getValue(dp[i]);
			}
		}
		model.addAttribute(getKey(), ergebnis);
	}

	public abstract double getValue(Datapoint dp);
	public abstract String getKey();
	
	public class DistanzDurchschnitt extends Durchschnitt {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getDistance();
		}

		@Override
		public String getKey() {
			return "Distanz_max";
		}

	}
	
	public class ZeitDurchschnitt extends Durchschnitt {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getTime();
		}
		
		@Override
		public String getKey() {
			return "Zeit_max";
		}

	}
	
	public class GeschwindigkeitDurchschnitt extends Durchschnitt {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getDistance() / dp.getTime();
		}
		
		@Override
		public String getKey() {
			return "Geschwindigkeit_max";
		}

	}
	
}
