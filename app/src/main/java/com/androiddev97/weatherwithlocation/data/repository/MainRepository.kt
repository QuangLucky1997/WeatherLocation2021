package com.androiddev97.weatherwithlocation.data.repository
import com.androiddev97.weatherwithlocation.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getDataWeather(cityName:String,appId:String) = apiHelper.getDataWeather(cityName,appId)

}