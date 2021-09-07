package org.geosopra;

public abstract class OutlierdetectionMax implements AnalystIn {
	
	@Override
	public double analyse(Datapoint[] dp) {
		double ergebnis = 0;
		for(int i = 0; i < dp.length; i++) {
			if (getValue(dp[i]) > ergebnis) {
				ergebnis = getValue(dp[i]);
			}
		}
		return ergebnis;
	}

protected abstract double getValue(Datapoint dp);
	
	public class DistanzDurchschnitt extends Durchschnitt {

		@Override
		protected double getValue(Datapoint dp) {
			return dp.getDistance();
		}

	}
	
	public class ZeitDurchschnitt extends Durchschnitt {

		@Override
		protected double getValue(Datapoint dp) {
			return dp.getTime();
		}

	}
	
	public class GeschwindigkeitDurchschnitt extends Durchschnitt {

		@Override
		/**
		 * @return Durchschnittsgeschwindigkeit in km/h
		 */
		protected double getValue(Datapoint dp) {
			return dp.getDistance() / dp.getTime();
			}

		}
	}
}



}
