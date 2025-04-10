package com.project.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

//Here we are deserializing JSON to POJO and taking only required field

@Getter
@Setter
public class WeatherResponse {

    private Current current;

    @Getter
    @Setter
    public class Current{
        @JsonProperty("observation_time")
        private String observationTime;


        private int temperature;

        @JsonProperty("weather_code")
        private int weatherCode;

        @JsonProperty("weather_icons")
        private ArrayList<String> weatherIcons;


        @JsonProperty("wind_speed")
        private int windSpeed;

        @JsonProperty("wind_dir")
        private String windDir;

        private int pressure;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;


    }


}



