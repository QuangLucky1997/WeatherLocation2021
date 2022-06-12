package com.androiddev97.weatherwithlocation.data.model.fiveday;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("main")
@Expose
public String main;
@SerializedName("description")
@Expose
public String description;
@SerializedName("icon")
@Expose
public String icon;

}