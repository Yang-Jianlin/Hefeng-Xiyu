package com.fukaimei.speechrecognizer.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {

    @SerializedName("location")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

//    public Update update;
//
//    public class Update {
//
//        @SerializedName("loc")
//        public String updateTime;
//
//    }

}
