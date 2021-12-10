package com.impact.thebestweather.ui.weather

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.impact.thebestweather.R
import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.weather.WeatherRemoteSourceImpl
import com.impact.thebestweather.models.weather.WeatherRequestData
import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.usecases.GetWeatherUseCase
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    application: Application) : AndroidViewModel(application) {
    private val TAG = "WeatherViewModel"
    private val compositeDisposable = CompositeDisposable()
    private val application: Application? = getApplication()

    private val _weatherLiveData = MutableLiveData<Weather>()
    val weatherLiveData: LiveData<Weather>
        get() = _weatherLiveData

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState
    private val _currentWeatherLiveData = MutableLiveData<CurrentWeather>()
    val currentWeatherLiveData: LiveData<CurrentWeather>
        get() = _currentWeatherLiveData
    private val _lastCityLiveData = MutableLiveData<String>()
    val lastCityLiveData: LiveData<String>
        get() = _lastCityLiveData


    fun getWeather(weatherRequestData: WeatherRequestData) {
        try {

            _loadingState.value = LoadingState.LOADING
            compositeDisposable.add(getWeatherUseCase.execute(weatherRequestData)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weather ->
                        _weatherLiveData.postValue(weather)
                        _currentWeatherLiveData.postValue(weather.currentWeather)
                        Log.d(TAG, "GetWeatherUseCase:success/ ${weather.dailyData}")
                        Log.d(TAG, "GetWeatherUseCase:current/ ${weather.currentWeather}")
                        _loadingState.value = LoadingState.LOADED
                    }, { e ->
                        Log.d(TAG, "GetWeatherUseCase:e/ ${e.message}")
                        _loadingState.value = LoadingState.error(e.message)
                    })
            )
        } catch (e: Exception) {
            _loadingState.value = LoadingState.error(e.message)
        }
    }



     fun getWeatherRequest(navController: NavController): WeatherRequestData? {
        var boolean: Boolean? = null
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
        val shp = application?.getSharedPreferences("lastRequestShP", Context.MODE_PRIVATE)
        val lastCityKey = shp?.getString("lastCityKey", "")
        val lastCityName = shp?.getString("lastCityName", "")
        Log.d(TAG, "defaultCity: $boolean")
        if (lastCityKey.isNullOrEmpty()) {
            navController.navigate(R.id.action_navigation_home_to_navigation_city)
        } else {
            _lastCityLiveData.value = lastCityName.toString()
            Log.d(TAG, "lastCityKey: $lastCityKey")
            Log.d(TAG, "lastCityName: $lastCityName")
            val mValues = sharedPreferences.getString("metricValues", "")
            boolean = mValues.equals("metric")
            return WeatherRequestData(
                lastCityKey,
                Constant.API_KEY,
                "en",
                "false",
                boolean.toString()
            )
        }
        return null
    }

    override fun <T : Application?> getApplication(): T {
        return super.getApplication()

    }


}
