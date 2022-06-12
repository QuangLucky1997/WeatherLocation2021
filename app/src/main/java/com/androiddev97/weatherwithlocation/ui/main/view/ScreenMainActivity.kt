package com.androiddev97.weatherwithlocation.ui.main.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.androiddev97.weatherwithlocation.R
import com.androiddev97.weatherwithlocation.data.api.ApiHelper
import com.androiddev97.weatherwithlocation.data.api.RetrofitBuilder
import com.androiddev97.weatherwithlocation.data.model.fiveday.WeatherRespone
import com.androiddev97.weatherwithlocation.ui.base.DataWeatherViewModelFactory
import com.androiddev97.weatherwithlocation.ui.main.viewmodel.DataWeatherViewModel
import com.androiddev97.weatherwithlocation.utils.App
import com.androiddev97.weatherwithlocation.utils.Resource
import com.androiddev97.weatherwithlocation.utils.Status
import kotlinx.android.synthetic.main.activity_screen_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.String


class ScreenMainActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: DataWeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_screen_main)
        super.onCreate(savedInstanceState)
        setUpViewModel()
        setUpObserver()

    }


    private fun setUpViewModel() {
        val viewModelWeatherFactory =
            DataWeatherViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        weatherViewModel =
            ViewModelProvider(
                this,
                viewModelWeatherFactory
            ).get(DataWeatherViewModel::class.java)
    }

    private fun setUpObserver() {
        weatherViewModel.getDataWeather("Hanoi", App.APP_ID)
            .observe(this) { data ->
                Log.d("MainLocation", data.toString())
                getDataWeather(data)
            }
    }


    private fun getDataWeather(it: Resource<WeatherRespone>) {
        it.let {
            when (it.status) {
                Status.SUCCESS -> {
                    val data = it.data
                    Log.d("MainLocation", data.toString())
                    progress_bar.visibility = View.GONE
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.Main)
                        {
                            setDataWeather(data as WeatherRespone)
                        }
                    }
                }
                Status.LOADING -> {
                    progress_bar.visibility = View.GONE
                    Log.e("Loading", "${it.message}")
                }
                Status.ERROR -> {
                    Log.e("error", "${it.message}")
                }
            }
        }
    }

    private fun setDataWeather(weatherData: WeatherRespone) {
        textToDay.text = String.valueOf(weatherData.list[0].dt)
        cityName_text.text = weatherData.city.name
        dataTempToday.text = weatherData.list[0].weather[0].main
        when (weatherData.list[0].weather[0].main) {
            "Clouds" -> img_weather_today.setImageResource(R.drawable.overclouds)
            "Rain" -> img_weather_today.setImageResource(R.drawable.lightrain)
        }
//        txtCityName.text = weatherData.city.name
//        txtCountry.text = weatherData.city.country
//        val simpleDateFormat = SimpleDateFormat("HH:mm")
//        val sunRise = simpleDateFormat.format(weatherData.city.sunrise)
//        text_view_sun_rise.text = sunRise
//        val sunSet = simpleDateFormat.format(weatherData.city.sunset)
//        text_view_sun_set.text = sunSet
//        val dayFirst: String = valueOf(weatherData.list[0].dt)
//        val l = Long.valueOf(dayFirst)
//        val date = Date(l * 1000L)
//        val simpleDateFormatDay = SimpleDateFormat("EEEE,d MMM", Locale.ENGLISH)
//        val day = simpleDateFormatDay.format(date)
//        txtDay.text = day
//        val seaLevel = weatherData.list[0].main.seaLevel
//        val humidity = weatherData.list[0].main.humidity
//        val wind = weatherData.list[0].wind.speed
//        text_view_sea.text = seaLevel.toString()
//        text_view_humidityIndex.text = "$humidity%"
//        text_view_wind.text = "$wind m/S "
//
//        val dayNext1: String = valueOf(weatherData.list[6].dt)
//        val lNext1 = Long.valueOf(dayNext1)
//        val dateNext1 = Date(lNext1 * 1000L)
//        val simpleDateFormatDayNext1 = SimpleDateFormat("EEEE", Locale.ENGLISH)
//        val day1 = simpleDateFormatDayNext1.format(dateNext1)
//        text_day_1.text = day1
//
//        val dayNext2: String = valueOf(weatherData.list[14].dt)
//        val lNext2 = Long.valueOf(dayNext2)
//        val dateNext2 = Date(lNext2 * 1000L)
//        val simpleDateFormatDayNext2 = SimpleDateFormat("EEEE", Locale.ENGLISH)
//        val day2 = simpleDateFormatDayNext2.format(dateNext2)
//        text_day_2.text = day2
//
//        val dayNext3: String = valueOf(weatherData.list[22].dt)
//        val lNext3 = Long.valueOf(dayNext3)
//        val dateNext3 = Date(lNext3 * 1000L)
//        val simpleDateFormatDayNext3 = SimpleDateFormat("EEEE", Locale.ENGLISH)
//        val day3 = simpleDateFormatDayNext3.format(dateNext3)
//        text_day_3.text = day3
//
//        val dayNext4: String = valueOf(weatherData.list[30].dt)
//        val lNext4 = Long.valueOf(dayNext4)
//        val dateNext4 = Date(lNext4 * 1000L)
//        val simpleDateFormatDayNext4 = SimpleDateFormat("EEEE", Locale.ENGLISH)
//        val day4 = simpleDateFormatDayNext4.format(dateNext4)
//        text_day_4.text = day4
//
//        val dayNext5: String = valueOf(weatherData.list[38].dt)
//        val lNext5 = Long.valueOf(dayNext5)
//        val dateNext5 = Date(lNext5 * 1000L)
//        val simpleDateFormatDayNext5 = SimpleDateFormat("EEEE", Locale.ENGLISH)
//        val day5 = simpleDateFormatDayNext5.format(dateNext5)
//        text_day_5.text = day5
//
//        text_view_humidity1.text = weatherData.list[6].main.humidity.toString() + "%"
//        text_view_humidity2.text = weatherData.list[14].main.humidity.toString() + "%"
//        text_view_humidity3.text = weatherData.list[22].main.humidity.toString() + "%"
//        text_view_humidity4.text = weatherData.list[30].main.humidity.toString() + "%"
//        text_view_humidity5.text = weatherData.list[38].main.humidity.toString() + "%"
//
//
//        val icon1 = weatherData.list[6].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$icon1.png").into(image_view_weather1)
//        val icon2 = weatherData.list[14].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$icon2.png").into(image_view_weather2)
//        val icon3 = weatherData.list[22].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$icon3.png").into(image_view_weather3)
//        val icon4 = weatherData.list[30].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$icon4.png").into(image_view_weather4)
//        val icon5 = weatherData.list[38].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$icon5.png").into(image_view_weather5)
//
//
//        val tempMax1 = weatherData.list[6].main.tempMax - 273.15
//        val tempMax1CV = tempMax1.toInt()
//        val tempMin1 = weatherData.list[6].main.tempMin - 273.15
//        val tempMin1CV = tempMin1.toInt()
//        tempMinMax.text = "$tempMax1CV°C/$tempMin1CV°C"
//
//        val tempMax2 = weatherData.list[14].main.tempMax - 273.15
//        val tempMax2CV = tempMax2.toInt()
//        val tempMin2 = weatherData.list[14].main.tempMin - 273.15
//        val tempMin2CV = tempMin2.toInt()
//        tempMinMax2.text = "$tempMax2CV°C/$tempMin2CV°C"
//
//        val tempMax3 = weatherData.list[22].main.tempMax - 273.15
//        val tempMax3CV = tempMax3.toInt()
//        val tempMin3 = weatherData.list[22].main.tempMin - 273.15
//        val tempMin3CV = tempMin3.toInt()
//        tempMinMax3.text = "$tempMax3CV°C/$tempMin3CV°C"
//
//        val tempMax4 = weatherData.list[30].main.tempMax - 273.15
//        val tempMax4CV = tempMax4.toInt()
//        val tempMin4 = weatherData.list[30].main.tempMin - 273.15
//        val tempMin4CV = tempMin4.toInt()
//        tempMinMax4.text = "$tempMax4CV°C/$tempMin4CV°C"
//
//        val tempMax5 = weatherData.list[38].main.tempMax - 273.15
//        val tempMax5CV = tempMax5.toInt()
//        val tempMin5 = weatherData.list[38].main.tempMin - 273.15
//        val tempMin5CV = tempMin5.toInt()
//        tempMinMax5.text = "$tempMax5CV°C/$tempMin5CV°C"
//
//
//        val hour1 = weatherData.list[0].dtTxt
//        text_view_hour1.text = hour1.substring(11, 16)
//
//        val hour2 = weatherData.list[1].dtTxt
//        text_view_hour2.text = hour2.substring(11, 16)
//
//        val hour3 = weatherData.list[2].dtTxt
//        text_view_hour3.text = hour3.substring(11, 16)
//
//        val hour4 = weatherData.list[3].dtTxt
//        text_view_hour4.text = hour4.substring(11, 16)
//
//
//        val iconToday1 = weatherData.list[0].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$iconToday1.png").into(image_weather_1)
//        val iconToday2 = weatherData.list[1].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$iconToday2.png").into(image_weather_2)
//        val iconToday3 = weatherData.list[2].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$iconToday3.png").into(image_weather_3)
//        val iconToday4 = weatherData.list[3].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$iconToday4.png").into(image_weather_4)
//
//
//        val temp1 = weatherData.list[0].main.temp - 273.15
//        val temp1CV = temp1.toInt()
//        temp_h1.text = "$temp1CV°C"
//
//        val temp2 = weatherData.list[1].main.temp - 273.15
//        val temp2CV = temp2.toInt()
//        temp_h2.text = "$temp2CV°C"
//
//        val temp3 = weatherData.list[2].main.temp - 273.15
//        val temp3CV = temp3.toInt()
//        temp_h3.text = "$temp3CV°C"
//
//        val temp4 = weatherData.list[3].main.temp - 273.15
//        val temp4CV = temp4.toInt()
//        temp_h4.text = "$temp4CV°C"
//
//
//        humidity_h1.text = weatherData.list[0].main.humidity.toString() + "%"
//        humidity_h2.text = weatherData.list[1].main.humidity.toString() + "%"
//        humidity_h3.text = weatherData.list[2].main.humidity.toString() + "%"
//        humidity_h4.text = weatherData.list[3].main.humidity.toString() + "%"
//
//
//        val iconToday = weatherData.list[0].weather[0].icon
//        Picasso.get().load("http://openweathermap.org/img/wn/$iconToday.png").into(img_today)
//        val tempToday = weatherData.list[0].main.temp - 273.15
//        val tempTodayCV = tempToday.toInt()
//        temp_today.text = "$tempTodayCV°C"
//
//        val feelTemp = weatherData.list[0].main.feelsLike - 273.15
//        val feelTempCV = feelTemp.toInt()
//        txt_feel_temp.text = getString(R.string.feel_like) + "$feelTempCV°C"


    }

}