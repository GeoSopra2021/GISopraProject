package org.geosopra;

import org.springframework.ui.Model;

public interface AnalystIn {

	public double analyse(Datapoint[] dp, Model model);

	
	public abstract double getValue(Datapoint dp);

}
