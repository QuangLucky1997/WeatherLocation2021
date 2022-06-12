package com.androiddev97.weatherwithlocation.data.model.fiveday;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

@SerializedName("speed")
@Expose
public Double speed;
@SerializedName("deg")
@Expose
public Integer deg;
@SerializedName("gust")
@Expose
public Double gust;

}