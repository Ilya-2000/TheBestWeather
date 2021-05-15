package com.impact.thebestweather.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.impact.thebestweather.data.CitySource
import com.impact.thebestweather.models.location.LocationRequest
import com.impact.thebestweather.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

class CityViewModel : ViewModel() {
    private val TAG = "CityViewModel"
    private val compositeDisposable = CompositeDisposable()
    lateinit var citySource: CitySource


    fun searchCity(text: String) {
        try {
            citySource.searchCity(compositeDisposable,
                LocationRequest(Constant.API_KEY, text, "en", "false"))
        } catch (e: Exception) {

        }
    }



}