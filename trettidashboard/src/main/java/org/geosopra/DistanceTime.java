package org.geosopra;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class DistanceTime {

    public double[] getDistanceTimeMatrix(double lat1, double lon1, double lat2, double lon2) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", "5b3ce3597851110001cf62483194512efdbe471e8b6d024358d4fcd7");
        headers.set("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8");
        headers.set("Content-Type", "application/json; charset=utf-8");

        String payload = "{\"locations\":[[" + lon1 + "," + lat1 + "],[" + lon2 + "," + lat2 + "]],\"metrics\":[\"distance\",\"duration\"]}";
        HttpEntity<String> request =
                new HttpEntity<String>(payload, headers);

        BicycleRoute route =
                restTemplate.postForObject(
                        "http://10.67.116.55:8080/ors/v2/matrix/cycling-regular",
                        request,
                        BicycleRoute.class);

        double duration = (route.durations[0][1] + route.durations[1][0]) / 2;
        double distance = (route.distances[0][1] + route.distances[1][0]) / 2;

        return new double[]{duration, distance};


    }
}
