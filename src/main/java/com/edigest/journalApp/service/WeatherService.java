package com.edigest.journalApp.service;

import com.edigest.journalApp.cache.AppCache;
import com.edigest.journalApp.entity.ZipResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String apiKey = "d6733cf95db2cba9b53a17b257803d45";
    //    String urlString = "https://api.zippopotam.us/us/33162";//weather_api
    @Autowired
    AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public void getWeatherData() {
        ResponseEntity<ZipResponse> response = restTemplate.exchange(appCache.APP_CACHE.get("weather_api"), HttpMethod.GET, null, ZipResponse.class);
        ZipResponse zipResponse = response.getBody();
        System.out.println(zipResponse);
    }
}
