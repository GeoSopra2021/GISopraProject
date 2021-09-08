package org.geosopra;

public abstract class Kosten implements AnalystIn{

	@Override
	public double analyse(Datapoint[] dp) {
		Durchschnitt.ZeitDurchschnitt ds = new Durchschnitt.ZeitDurchschnitt();
		
		return getPreis().getGb() + ds.analyse(dp) * 100 * getPreis().getPpm();
	}
	
	public abstract Preis getPreis();

	@Override
	public double getValue(Datapoint dp) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class Tretty extends Kosten{
		
		public Preis roller = new Preis(0.99, 0.06);

		@Override
		public Preis getPreis() {
			return roller;
		}
		
	}
	
	public static class Lime extends Kosten{
		
		public Preis roller = new Preis(1.0, 0.2);

		@Override
		public Preis getPreis() {
			return roller;
		}
		
	}
	
	public static class Tier extends Kosten{
		
		public Preis roller = new Preis(1.0, 0.15);

		@Override
		public Preis getPreis() {
			return roller;
		}
		
	}

}
