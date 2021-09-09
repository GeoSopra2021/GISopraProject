package org.geosopra;

import org.springframework.ui.Model;

public abstract class Kosten implements AnalystIn{
	
	@Override
	public void analyse(Datapoint[] dp, Model model) {
		Durchschnitt.ZeitDurchschnitt ds = new Durchschnitt.ZeitDurchschnitt();
		
		double ergebnis = getPreis().getGb() + ds.analyseIntern(dp) * 100 * getPreis().getPpm();
		
		ergebnis = Math.round(ergebnis * 100)/100d;
		
		model.addAttribute(getKey(), ergebnis);
	}
	
	
	public double analyseIntern(Datapoint[] dp) {
		Durchschnitt.ZeitDurchschnitt ds = new Durchschnitt.ZeitDurchschnitt();
		
		double ergebnis = getPreis().getGb() + ds.analyseIntern(dp) * 100 * getPreis().getPpm();
		
		return ergebnis;
	}
	
	public abstract Preis getPreis();
	public abstract String getKey();

	@Override
	public double getValue(Datapoint dp) {
		
		return 0;
	}
	
	public static class Tretty extends Kosten{
		
		public Preis roller = new Preis(0.99, 0.06);

		@Override
		public Preis getPreis() {
			return roller;
		}

		@Override
		public String getKey() {
			return "Kosten_Tretty";
		}
		
	}
	
	public static class Lime extends Kosten{
		
		public Preis roller = new Preis(1.0, 0.2);

		@Override
		public Preis getPreis() {
			return roller;
		}
		
		@Override
		public String getKey() {
			return "Kosten_Lime";
		}
		
	}
	
	public static class Tier extends Kosten{
		
		public Preis roller = new Preis(1.0, 0.15);

		@Override
		public Preis getPreis() {
			return roller;
		}
		
		@Override
		public String getKey() {
			return "Kosten_Tier";
		}
		
	}

}
