package com.androiddev97.weatherwithlocation.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.androiddev97.weatherwithlocation.utils.Resource
import com.androiddev97.weatherwithlocation.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class DataWeatherViewModel(private val mainRepository: MainRepository):ViewModel() {
   fun getDataWeather(cityName:String,appId:String)= liveData(Dispatchers.IO){
       emit(Resource.loading(data = null))
       try {
           emit(Resource.success(data = mainRepository.getDataWeather(cityName, appId)))
       } catch (exception: Exception) {
           emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
       }
   }

   }
