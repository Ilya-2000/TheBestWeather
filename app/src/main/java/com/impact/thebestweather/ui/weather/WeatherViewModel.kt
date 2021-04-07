package com.impact.thebestweather.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.impact.thebestweather.data.repository.WeatherRepository
import com.impact.thebestweather.models.DailyWeather
import com.impact.thebestweather.models.WeatherRequest
import com.impact.thebestweather.utils.Constant
import io.reactivex.disposables.CompositeDisposable

class WeatherViewModel : ViewModel() {
    private val TAG = "WeatherViewModel"
    private val compositeDisposable = CompositeDisposable()
    private val _weatherLiveData = MutableLiveData<DailyWeather>()
    val weatherLiveData: LiveData<DailyWeather>
        get() = _weatherLiveData


    fun getWeather() {
        WeatherRepository.getWeather(compositeDisposable, WeatherRequest(51.2305015, 58.4738015,
                "hourly, daily",
                Constant.API_KEY ))?.let { setWeatherLiveData(it) }
        Log.d(TAG, "getWeather")
        Log.d(TAG, "getWeather/data: ${weatherLiveData.value}")
    }

    private fun setWeatherLiveData(dailyWeather: DailyWeather) {
        _weatherLiveData.value = dailyWeather


    }


}
