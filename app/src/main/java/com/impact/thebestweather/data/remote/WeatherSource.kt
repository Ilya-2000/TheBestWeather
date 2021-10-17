package com.impact.thebestweather.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.models.weather.WeatherRequestData
import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.network.WeatherApiService
import com.impact.thebestweather.utils.LoadingState
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

class WeatherSource() {
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }

    fun getWeather(weatherRequestData: WeatherRequestData): Single<Weather> {
        return Single.zip(
                weatherApiService.getHourlyWeatherFromNetwork(weatherRequestData.id,
                        weatherRequestData.apiKey, weatherRequestData.language, weatherRequestData.details, weatherRequestData.metric),
                weatherApiService.getDailyWeatherFromNetwork(weatherRequestData.id, weatherRequestData.apiKey,
                        weatherRequestData.language, weatherRequestData.details, weatherRequestData.metric),
                weatherApiService.getCurrentWeather(weatherRequestData.id,
                        weatherRequestData.apiKey, weatherRequestData.language, weatherRequestData.details),
                Function3<HourlyData, DailyData, CurrentWeather, Weather>{
                    t1: HourlyData, t2: DailyData, t3: CurrentWeather ->
                    createWeatherModel(t1, t2, t3)
                }
        )

    }




    private fun createWeatherModel(t1: HourlyData, t2: DailyData, t3: CurrentWeather): Weather {
        return Weather(t1, t2, t3)
    }


}