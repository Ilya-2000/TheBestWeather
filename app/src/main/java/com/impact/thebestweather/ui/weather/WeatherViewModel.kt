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
import com.impact.thebestweather.network.CityApiService
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel : ViewModel() {
    private val TAG = "WeatherViewModel"
    private val compositeDisposable = CompositeDisposable()
    lateinit var weatherSource: WeatherSource

    val cityApiService by lazy {
        CityApiService.create()
    }

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


    fun getWeather(weatherRequest: WeatherRequest) {
        try {
            weatherSource = WeatherSource()
            _loadingState.value = LoadingState.LOADING
            weatherSource.getWeather(compositeDisposable, weatherRequest)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weather ->
                        _hourlyWeatherLiveData.postValue(weather.hourlyData)
                        _dailyWeatherLiveData.postValue(weather.dailyData)
                        _currentWeatherLiveData.postValue(weather.currentWeather)
                        Log.d(TAG, "getWeather:success/ ${weather.dailyData}")
                        _loadingState.value = LoadingState.LOADED
                    }, { e ->
                        Log.d(TAG, "getWeather:e/ ${e.message}")
                        _loadingState.value = LoadingState.error(e.message)
                    })
        } catch (e: Exception) {
            _loadingState.value = LoadingState.error(e.message)
        }
    }


}
