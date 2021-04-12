package com.fukaimei.speechrecognizer.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("date")
    public String date;

    @SerializedName("tmp_max")
    public String temmax;

    @SerializedName("tmp_min")
    public String temmin;

    @SerializedName("cond_txt_d")
    public String condday;

    @SerializedName("cond_txt_n")
    public String condnight;

}
