package com.fukaimei.speechrecognizer.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    public String status;

    public Basic basic;

   // public AQI aqi;

    public Now now;

    @SerializedName("lifestyle")
    public List<Lifestyle> lifestyleList;

    public Update update;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

}
