package com.impact.thebestweather.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.impact.thebestweather.data.WeatherSource
import com.impact.thebestweather.data.repository.WeatherRepository
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.utils.Constant
import io.reactivex.disposables.CompositeDisposable

class WeatherViewModel : ViewModel() {
    private val TAG = "WeatherViewModel"
    private val compositeDisposable = CompositeDisposable()
    lateinit var weatherSource: WeatherSource
    private val _dailyWeatherLiveData = MutableLiveData<DailyData>()
    val dailyWeatherLiveData: LiveData<DailyData>
        get() = _dailyWeatherLiveData
    private val _hourlyWeatherLiveData = MutableLiveData<HourlyData>()
    val hourlyWeatherLiveData: LiveData<HourlyData>
        get() = _hourlyWeatherLiveData





    fun getDailyWeather() {
        WeatherRepository.getDailyWeather(compositeDisposable, WeatherRequest("289748", Constant.API_KEY, "ru",
        "false", "true"))?.let { setDailyWeatherLiveData(it) }
        Log.d(TAG, "getDailyWeather")
        Log.d(TAG, "getDailyWeather/data: ${dailyWeatherLiveData.value}")

    }

    fun getHourlyWeather() {
        /*WeatherRepository.getHourlyWeather(compositeDisposable, WeatherRequest("289748", Constant.API_KEY, "ru",
                "false", "true"))?.let { setHourlyWeatherLiveData(it) }*/
        weatherSource = WeatherSource()
        weatherSource.getHourlyWeather(compositeDisposable, WeatherRequest("289748",
                Constant.API_KEY, "ru",
                "false", "true"))
        weatherSource.hourlyWeatherLiveData.value?.let { setHourlyWeatherLiveData(it) }

    }

    private fun setDailyWeatherLiveData(dailyWeather: DailyData) {
        _dailyWeatherLiveData.value = dailyWeather

    }

    private fun setHourlyWeatherLiveData(hourlyData: HourlyData) {
        _hourlyWeatherLiveData.value = hourlyData
        Log.d(TAG, "setHourlyWeatherLiveData/hourlyData: $hourlyData")
        Log.d(TAG, "setHourlyWeatherLiveData: ${hourlyWeatherLiveData.value}")

    }


}
