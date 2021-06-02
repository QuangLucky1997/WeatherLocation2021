package com.androiddev97.weatherwithlocation.data.api

import com.androiddev97.weatherwithlocation.data.api.ApiService

class ApiHelper(private val apiService: ApiService) {
    suspend fun getDataWeather(cityName: String,appID:String) =
        apiService.getListFiveDay(cityName, appID)

}