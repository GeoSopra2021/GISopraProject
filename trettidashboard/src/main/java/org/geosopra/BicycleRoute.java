package org.geosopra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BicycleRoute {

    public double[][] durations;
    public double[][] distances;

}
