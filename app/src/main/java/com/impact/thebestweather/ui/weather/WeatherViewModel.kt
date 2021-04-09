package com.impact.thebestweather.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.impact.thebestweather.data.repository.WeatherRepository
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.utils.Constant
import io.reactivex.disposables.CompositeDisposable

class WeatherViewModel : ViewModel() {
    private val TAG = "WeatherViewModel"
    private val compositeDisposable = CompositeDisposable()
    private val _weatherLiveData = MutableLiveData<DailyData>()
    val weatherLiveData: LiveData<DailyData>
        get() = _weatherLiveData


    fun getWeather() {
        WeatherRepository.getWeather(compositeDisposable, WeatherRequest(Constant.API_KEY, "ru",
        "false", "true"))?.let { setWeatherLiveData(it) }
        Log.d(TAG, "getWeather")
        Log.d(TAG, "getWeather/data: ${weatherLiveData.value}")
    }

    private fun setWeatherLiveData(dailyWeather: DailyData) {
        _weatherLiveData.value = dailyWeather


    }


}
