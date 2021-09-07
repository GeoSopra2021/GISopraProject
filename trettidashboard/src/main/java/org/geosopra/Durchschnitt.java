package org.geosopra;

public abstract class Durchschnitt implements AnalystIn {

	@Override
	public double analyse(Datapoint[] dp) {
		double ergebnis = 0;
		for(int i = 0; i < dp.length; i++) {
			ergebnis += getValue(dp[i]);
		}
		
		ergebnis /= dp.length;
		return ergebnis;
	}
	
	public class DistanzDurchschnitt extends Durchschnitt {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getDistance();
		}

	}
	
	public class ZeitDurchschnitt extends Durchschnitt {

		@Override
		public double getValue(Datapoint dp) {
			return dp.getTime();
		}

	}
	
	public class GeschwindigkeitDurchschnitt extends Durchschnitt {

		@Override
		/**
		 * @return Durchschnittsgeschwindigkeit in km/h
		 */
		public double getValue(Datapoint dp) {
			return dp.getDistance() / dp.getTime();
		}

	}


}
