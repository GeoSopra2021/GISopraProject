package org.geosopra;

import org.springframework.ui.Model;

public abstract class Durchschnitt implements AnalystIn {
	public abstract String getKey();

	@Override
	public void analyse(Datapoint[] dp, Model model) {
		double ergebnis = 0;
		for(int i = 0; i < dp.length; i++) {
			ergebnis += getValue(dp[i]);
			}
		
		ergebnis /= dp.length;
		ergebnis = Math.round(ergebnis * 100.0)/100.0;
		model.addAttribute(getKey(), ergebnis);
	}
	
	public double analyseIntern(Datapoint[] dp) {
		double ergebnis = 0;
		for(int i = 0; i < dp.length; i++) {
			ergebnis += getValue(dp[i]);
		}
		
		ergebnis /= dp.length;
		return ergebnis;
	}
	
	public static class DistanzDurchschnitt extends Durchschnitt {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getRoutingDistance();
		}

		@Override
		public String getKey() {
			return "DistanzDurchschnitt";
		}
	}
	
	public static class ZeitDurchschnitt extends Durchschnitt {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getRoutingTime();
		}
		@Override
		public String getKey() {
			return "ZeitDurchschnitt";
		}

	}
	
	public static class GeschwindigkeitDurchschnitt extends Durchschnitt {

		@Override
		/**
		 * @return Durchschnittsgeschwindigkeit in km/h
		 */
		public double getValue(Datapoint dp) {

			System.out.println("Durchschnittsgeschwindigkeit: "+ dp.getRoutingDistance() / dp.getRoutingTime());
			return dp.getRoutingDistance() / dp.getRoutingTime();

		}

		/**
		 * If-Abfrage zum Abfangen von NaN Werten der Routing Time
		 */
		
		@Override
		public void analyse(Datapoint[] dp, Model model) {
			double ergebnis = 0;
			for(int i = 0; i < dp.length; i++) {
				if (dp[i].getRoutingTime() >0) {
					ergebnis += getValue(dp[i]);
				}
			}


			ergebnis /= dp.length;
			ergebnis = Math.round(ergebnis * 100.0)/100.0;
			model.addAttribute(getKey(), ergebnis);
		}


		@Override
		public String getKey() {
			return "GeschwindigkeitDurchschnitt";
		}

	}


}
