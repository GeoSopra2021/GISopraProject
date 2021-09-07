package org.geosopra;

public interface AnalystIn {
	
	public double analyse(Datapoint[] dp);
	
	public abstract double getValue(Datapoint dp);

}
