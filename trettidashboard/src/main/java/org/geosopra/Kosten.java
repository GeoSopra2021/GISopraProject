package org.geosopra;

public class Kosten implements AnalystIn{

	@Override
	public double analyse(Datapoint[] dp) {
		Durchschnitt.ZeitDurchschnitt ds = new Durchschnitt.ZeitDurchschnitt();
		
		return 0.99 + ds.analyse(dp) * 100 * 0.06;
	}

	@Override
	public double getValue(Datapoint dp) {
		// TODO Auto-generated method stub
		return 0;
	}

}
