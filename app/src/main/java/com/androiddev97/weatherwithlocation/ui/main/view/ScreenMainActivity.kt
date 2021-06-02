package com.androiddev97.weatherwithlocation.ui.main.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.*
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.androiddev97.weatherwithlocation.ui.fragment.WeatherFragment
import com.androiddev97.weatherwithlocation.R
import com.androiddev97.weatherwithlocation.utils.App
import com.androiddev97.weatherwithlocation.utils.receiver.NetworkChangeReceiver
import java.util.*


class ScreenMainActivity : AppCompatActivity(), NetworkChangeReceiver.ConnectivityReceiverListener,
    LocationListener {
    private var isConnect = false
    private lateinit var mLocationManager: LocationManager
    private var mHasGps = false
    private var mHasNetWork = false
    private var mLocationGPS: Location? = null
    private var mLocationNetWork: Location? = null
    private lateinit var mAddress: Address


    private var mPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isNetworkConnected()) {
            setContentView(R.layout.activity_screen_main)
            registerBroadcast()
            addFragmentWeather()
            initCheckPermission()
        } else {
            Toast.makeText(this, "Please connect Internet", Toast.LENGTH_SHORT).show()
            setNoInternetLayout()
        }
    }

    private fun addFragmentWeather() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowTitleEnabled(false)
            show()
        }
        val fragmentHome = WeatherFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_layout_main, fragmentHome)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_city_name, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> {
                val intentSearch = Intent(this, SearchActivity::class.java)
                startActivity(intentSearch)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNoInternetLayout() {
        setContentView(R.layout.activity_no_connect_network)
        registerBroadcast()
        val btnTryAgain = findViewById<Button>(R.id.btnTryAgain)
        btnTryAgain?.setOnClickListener {
            if (isConnect) {
                setContentView(R.layout.activity_screen_main)
                addFragmentWeather()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        NetworkChangeReceiver.connectivityReceiverListener = this

    }

    private fun registerBroadcast() {
        registerReceiver(
            NetworkChangeReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }


    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isConnect = isConnected
        if (!isConnected) {
            Log.d("status", "No Internet ")
        }
    }

    private fun initCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(mPermissions)) {
                getLocation()
            } else {
                requestPermissions(mPermissions, PERMISSION_REQUEST)
            }
        } else {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mHasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        mHasNetWork = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (mHasGps || mHasNetWork) {
            if (mHasGps) {
                mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this
                )

                val localGpsLocation =
                    mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null) {
                    mLocationGPS = localGpsLocation
                }
            }
            if (mHasNetWork) {
                mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this
                )

                val localNetWorkLocation = mLocationManager.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER
                )
                if (localNetWorkLocation != null) {
                    mLocationNetWork = localNetWorkLocation
                }
            }
            if (mLocationGPS != null && mLocationNetWork != null) {
                if (mLocationGPS!!.accuracy > mLocationNetWork!!.accuracy) {
//                    textViewResult.append("\nNetWork at Fist")
//                    textViewResult.append("\nLatitude: " + mLocationNetWork!!.latitude)
//                    textViewResult.append("\nLongitude: " + mLocationNetWork!!.longitude)
                } else {
//                    textViewResult.append("\nGPS at Fist")
//                    textViewResult.append("\nLatitude: " + mLocationGPS!!.latitude)
//                    textViewResult.append("\nLongitude: " + mLocationGPS!!.longitude)

                }
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        mLocationManager.removeUpdates(this)
    }


    override fun onLocationChanged(location: Location?) {
        val getCityName=intent
        val rsCity=getCityName.getStringExtra(App.SEND_RS)
        if (mHasGps) {
            Log.d("GET_GPS_LOCATION", "hasGPS")
            if (location != null) {
                mLocationGPS = location
                val geocoder = Geocoder(this, Locale.getDefault())
                val listAddress: MutableList<Address> = geocoder.getFromLocation(
                    mLocationGPS!!.latitude,
                    mLocationGPS!!.longitude, 1
                )
                mAddress = listAddress[0]
                val cityName = mAddress.locality
                val weatherFragment = WeatherFragment()
                val bundle = Bundle()
                bundle.putString(App.YOUR_CITY, cityName)
                bundle.putString("citySearch",rsCity)
                val transaction = supportFragmentManager.beginTransaction()
                weatherFragment.arguments = bundle
                transaction.replace(R.id.frame_layout_main, weatherFragment)
                transaction.commit()
                Log.d("city", cityName)

            } else {
                Toast.makeText(this, "Location null", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("Not yet implemented")
    }

    private fun checkPermission(mPermissions: Array<String>): Boolean {
        var allSuccess = true
        for (i in mPermissions.indices) {
            if (checkCallingOrSelfPermission(mPermissions[i]) == PackageManager.PERMISSION_DENIED) {
                allSuccess = false
            }
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this, "Go to settings and enable the permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            if (allSuccess)
                getLocation()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST = 10

        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 0F

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES: Long = 1000 * 5 * 1
    }

}