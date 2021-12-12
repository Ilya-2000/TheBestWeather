package com.impact.thebestweather.city


import android.content.Context
import com.impact.thebestweather.models.LocationSharedData
import com.impact.thebestweather.models.location.LocationItem

private const val SELECTED_CITY_KEY = "selectedCityKey"
private const val SELECTED_CITY_NAME = "selectedCityName"
private const val PREFERENCES_NAME = "lastRequestShP"

class LocationLocalStorageImpl(private val context: Context) : LocationLocalStorage {
    private val shp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    override fun saveSelectCity(locationItem: LocationItem) {
        val edit = shp.edit()
        edit.putString(SELECTED_CITY_KEY, locationItem.Key)
        edit.putString(SELECTED_CITY_NAME, locationItem.EnglishName)
        edit.apply()


    }

    override fun getSelectedCity(): LocationSharedData? {
        val shp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val lastCityKey = shp?.getString(SELECTED_CITY_KEY, "")
        val lastCityName = shp?.getString(SELECTED_CITY_NAME, "")
        return lastCityKey?.let { lastCityName?.let { it1 -> LocationSharedData(it, it1) } }
    }
}