package com.fukaimei.speechrecognizer.gson;

import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond_txt")
    public String condtext;

   @SerializedName("fl")
    public String flwen;

    @SerializedName("hum")
    public String xhum;

    @SerializedName("vis")
    public String vision;

    @SerializedName("wind_sc")
    public String windsc;
//    @SerializedName("cond")
//    public More more;
//
//    public class More {
//
//        @SerializedName("txt")
//        public String info;
//
//    }

}
