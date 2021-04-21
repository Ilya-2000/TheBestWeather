package com.impact.thebestweather.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.impact.thebestweather.data.WeatherSource
import com.impact.thebestweather.data.repository.WeatherRepository
import com.impact.thebestweather.models.Resource
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState
    private val _currentWeatherLiveData = MutableLiveData<CurrentWeather>()
    val currentWeatherLiveData: LiveData<CurrentWeather>
        get() = _currentWeatherLiveData

    init {
        getWeather()
    }

    /*fun getHourly() {
        weatherSource = WeatherSource()
        weatherSource.getHourlyWeather(compositeDisposable, WeatherRequest("289748",
                Constant.API_KEY, "ru",
                "false", "true"))
    }*/

    private fun getWeather() {
        try {
            weatherSource = WeatherSource()
            _loadingState.value = LoadingState.LOADING
            /*WeatherRepository.getWeather(compositeDisposable, WeatherRequest("289748",
                    Constant.API_KEY, "ru",
                    "false", "true"))*/
            weatherSource.getWeather(compositeDisposable, WeatherRequest("289748",
                    Constant.API_KEY, "ru",
                    "false", "true"))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weather ->
                        _hourlyWeatherLiveData.postValue(weather.hourlyData)
                        _dailyWeatherLiveData.postValue(weather.dailyData)
                        Log.d(TAG, "getWeather:success/ $weather")
                        _loadingState.value = LoadingState.LOADED
                    }, { e ->
                        Log.d(TAG, "getWeather:e/ ${e.message}")
                        _loadingState.value = LoadingState.error(e.message)
                    })

           /* _loadingState.value = LoadingState.LOADED
            if (_loadingState.value == LoadingState.LOADED) {
                _hourlyWeatherLiveData.value
                /*_dailyWeatherLiveData.value = WeatherRepository.dailyData
                _hourlyWeatherLiveData.value = WeatherRepository.hourlyData
                _currentWeatherLiveData.value = WeatherRepository.currentWeather*/
            }*/
        } catch (e: Exception) {
            _loadingState.value = LoadingState.error(e.message)
        }



    }





}
