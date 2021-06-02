package com.androiddev97.weatherwithlocation.data.model.fiveday

data class list(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Float,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)