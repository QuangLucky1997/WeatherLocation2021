package com.androiddev97.weatherwithlocation.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddev97.weatherwithlocation.R
import com.androiddev97.weatherwithlocation.adapter.AdapterCityName
import com.androiddev97.weatherwithlocation.adapter.CityLocation
import com.androiddev97.weatherwithlocation.utils.App
import kotlinx.android.synthetic.main.activity_search.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity(), AdapterCityName.OnItemClickListener,
    SearchView.OnQueryTextListener {
    private lateinit var cityAdapterCityName: AdapterCityName
    private lateinit var arrayListCity: ArrayList<CityLocation>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        imgBackSearch.setOnClickListener {
            onBackPressed()
        }
        arrayListCity = ArrayList()
        arrayListCity.add(CityLocation("Paris"))
        arrayListCity.add(CityLocation("Madrid"))
        arrayListCity.add(CityLocation("Toronto"))
        arrayListCity.add(CityLocation("Berlin"))
        arrayListCity.add(CityLocation("Oslo"))
        arrayListCity.add(CityLocation("Seoul"))
        arrayListCity.add(CityLocation("Tokyo"))
        arrayListCity.add(CityLocation("Helsinki"))
        arrayListCity.add(CityLocation("Budapest"))
        arrayListCity.add(CityLocation("Milan"))
        arrayListCity.add(CityLocation("Kiev"))
        arrayListCity.add(CityLocation("Prague"))
        arrayListCity.add(CityLocation("New York"))
        arrayListCity.add(CityLocation("Chicago"))
        arrayListCity.add(CityLocation("Hanoi"))
        arrayListCity.add(CityLocation("Hue"))
        arrayListCity.add(CityLocation("Warsaw"))
        arrayListCity.add(CityLocation("Vienna"))
        arrayListCity.add(CityLocation("Dublin"))
        arrayListCity.add(CityLocation("Rome"))
        arrayListCity.add(CityLocation("Athens"))
        arrayListCity.add(CityLocation("Torino"))
        arrayListCity.add(CityLocation("Amsterdam"))
        arrayListCity.add(CityLocation("Riga"))
        arrayListCity.add(CityLocation("Brussels"))
        arrayListCity.add(CityLocation("Edinburgh"))
        arrayListCity.add(CityLocation("Lisbon"))
        arrayListCity.add(CityLocation("Stockholm"))
        arrayListCity.add(CityLocation("Monaco"))
        arrayListCity.add(CityLocation("Liverpool"))
        arrayListCity.add(CityLocation("Zurich"))
        arrayListCity.add(CityLocation("Geneva"))
        arrayListCity.add(CityLocation("Turin"))
        arrayListCity.add(CityLocation("Glasgow"))
        arrayListCity.add(CityLocation("Porto"))
        arrayListCity.add(CityLocation("Stavanger"))
        arrayListCity.add(CityLocation("Sofia"))
        arrayListCity.add(CityLocation("Nice"))
        arrayListCity.add(CityLocation("Malaga"))
        cityAdapterCityName = AdapterCityName(this)
        cityAdapterCityName.setData(arrayListCity)
        recycleViewSearch.layoutManager = LinearLayoutManager(this)
        recycleViewSearch.setHasFixedSize(true)
        recycleViewSearch.adapter = cityAdapterCityName
        searchControl()
    }


    private fun searchControl() {
        val searchView = searchNote
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        cityAdapterCityName.filter.filter(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        cityAdapterCityName.filter.filter(query)
        return true
    }

    override fun CLickCityName(city: CityLocation) {
        val citySend=Intent(this,ShowSearchAcitivity::class.java)
        citySend.putExtra(App.SEND_RS,city.name)
        startActivity(citySend)
    }
}

