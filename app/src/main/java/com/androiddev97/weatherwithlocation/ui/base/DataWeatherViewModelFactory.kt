package com.androiddev97.weatherwithlocation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddev97.weatherwithlocation.data.api.ApiHelper
import com.androiddev97.weatherwithlocation.data.repository.MainRepository
import com.androiddev97.weatherwithlocation.ui.main.viewmodel.DataWeatherViewModel

class DataWeatherViewModelFactory(private val apiHelper: ApiHelper) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataWeatherViewModel::class.java)) {
            return DataWeatherViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}