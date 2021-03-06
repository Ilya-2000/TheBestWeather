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
import com.impact.thebestweather.usecases.GetSelectedCityUseCase
import com.impact.thebestweather.usecases.GetWeatherUseCase
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
    application: Application) : AndroidViewModel(application) {
    private val TAG = "WeatherViewModel"
    private val compositeDisposable = CompositeDisposable()

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
        val locationShared = getSelectedCityUseCase.execute()
        if (locationShared?.key.isNullOrEmpty()) {
            navController.navigate(R.id.action_navigation_home_to_navigation_city)
        } else {
            _lastCityLiveData.value = locationShared?.name
            Log.d(TAG, "lastCityKey: ${locationShared?.key}")
            Log.d(TAG, "lastCityName: ${locationShared?.name}")
            return locationShared?.let {
                WeatherRequestData(
                    it.key,
                    Constant.API_KEY,
                    "en",
                    "false",
                    "metric"
                )
            }
        }
        return null
    }

    override fun <T : Application?> getApplication(): T {
        return super.getApplication()

    }


}
