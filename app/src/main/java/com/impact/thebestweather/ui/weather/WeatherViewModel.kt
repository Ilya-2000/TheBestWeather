package com.impact.thebestweather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.impact.thebestweather.data.repository.WeatherRepository
import com.impact.thebestweather.models.WeatherRequest
import com.impact.thebestweather.utils.Constant
import io.reactivex.disposables.CompositeDisposable

class WeatherViewModel : ViewModel() {
    private val TAG = "WeatherViewModel"
    private val compositeDisposable = CompositeDisposable()


    fun getWeather() {
        WeatherRepository.getWeather(compositeDisposable, WeatherRequest(51.2305015, 58.4738015,
            "hourly, daily",
            Constant.API_KEY ))
    }


}
