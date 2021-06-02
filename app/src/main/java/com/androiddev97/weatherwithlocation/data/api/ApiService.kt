package com.androiddev97.weatherwithlocation.data.api

import com.androiddev97.weatherwithlocation.data.model.fiveday.ForecastWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/data/2.5/forecast")
    suspend fun getListFiveDay(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): ForecastWeatherResponse


}