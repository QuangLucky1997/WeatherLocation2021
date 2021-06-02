package com.androiddev97.weatherwithlocation.data.model.fiveday

data class ForecastWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<list>,
    val message: Int
)

