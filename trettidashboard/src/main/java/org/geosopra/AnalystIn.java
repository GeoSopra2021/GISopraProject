package org.geosopra;

import org.springframework.ui.Model;

public interface AnalystIn {

	public void analyse(Datapoint[] dp, Model model);

	
	public abstract double getValue(Datapoint dp);


}
