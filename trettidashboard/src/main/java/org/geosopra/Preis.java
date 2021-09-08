package org.geosopra;

public class Preis {
	
	private double gb; // Grundgebühr
	private double ppm; // Preis pro Minute
	
	public Preis(double gb, double ppm) {
		
		this.setGb(gb);
		this.setPpm(ppm);
		
	}

	/**
	 * @return the gb
	 */
	public double getGb() {
		return gb;
	}

	/**
	 * @param gb the gb to set
	 */
	public void setGb(double gb) {
		this.gb = gb;
	}

	/**
	 * @return the ppm
	 */
	public double getPpm() {
		return ppm;
	}

	/**
	 * @param ppm the ppm to set
	 */
	public void setPpm(double ppm) {
		this.ppm = ppm;
	}

}
