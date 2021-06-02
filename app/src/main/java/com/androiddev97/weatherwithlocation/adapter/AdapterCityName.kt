package com.androiddev97.weatherwithlocation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.androiddev97.weatherwithlocation.R
import kotlinx.android.synthetic.main.city_custom.view.*

class AdapterCityName(var click: OnItemClickListener) : RecyclerView.Adapter<AdapterCityName.CityHolder>(),Filterable {
    lateinit var arrayCity: ArrayList<CityLocation>
    var cityFilterList = ArrayList<CityLocation>()

    class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        return CityHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.city_custom, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        val cityList = cityFilterList[position]
        holder.itemView.textViewCityName.text = cityList.name
        holder.itemView.textViewCityName.setOnClickListener {
            click.CLickCityName(cityList)
        }
    }

    fun setData(arrayCity: ArrayList<CityLocation>) {
        this.cityFilterList = arrayCity
    }

    override fun getItemCount(): Int {
        return cityFilterList.size
    }
    interface OnItemClickListener {
        fun CLickCityName(city:CityLocation)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var charSearch = p0.toString()
                if (charSearch.isEmpty()) {
                    cityFilterList = arrayCity
                } else {
                    val cityListFilter=ArrayList<CityLocation>()
                    for (cityName in arrayCity) {
                        if (cityName.name.toLowerCase().contains(charSearch.toLowerCase())) {
                            cityListFilter.add(cityName)
                        }
                        cityFilterList = cityListFilter
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = cityFilterList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                arrayCity = p1?.values as ArrayList<CityLocation>
                notifyDataSetChanged()
            }

        }
    }

}