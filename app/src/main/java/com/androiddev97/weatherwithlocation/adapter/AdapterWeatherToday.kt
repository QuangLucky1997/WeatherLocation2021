package com.androiddev97.weatherwithlocation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androiddev97.weatherwithlocation.R
import com.androiddev97.weatherwithlocation.data.model.fiveday.WeatherRespone
import kotlinx.android.synthetic.main.custom_recycle_today.view.*

class AdapterWeatherToday() : RecyclerView.Adapter<AdapterWeatherToday.TodayHolder>() {
    private var listWeatherData = emptyList<WeatherRespone>()

    class TodayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayHolder {
        return AdapterWeatherToday.TodayHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_recycle_today, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodayHolder, position: Int) {
        val dataWeather=listWeatherData[position]
        holder.itemView.text_temperature

    }

    override fun getItemCount(): Int {
        return listWeatherData.size
    }


    private fun setData(listWeather:List<WeatherRespone>){
        this.listWeatherData=listWeather
        notifyDataSetChanged()

    }
}