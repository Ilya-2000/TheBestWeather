package com.impact.thebestweather.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.impact.thebestweather.data.repository.WeatherRepository
import com.impact.thebestweather.models.Resource
import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.models.weather.current.CurrentWeather
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.network.WeatherApiService
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class WeatherSource() {
    private val TAG = "WeatherSource"
    private val _dailyWeatherLiveData = MutableLiveData<DailyData>()
    val dailyWeatherLiveData: LiveData<DailyData>
        get() = _dailyWeatherLiveData

    private val _hourlyWeatherLiveData = MutableLiveData<HourlyData>()
    val hourlyWeatherLiveData: LiveData<HourlyData>
        get() = _hourlyWeatherLiveData

    private val _currentWeatherLiveData = MutableLiveData<CurrentWeather>()
    val currentWeatherLiveData: LiveData<CurrentWeather>
        get() = _currentWeatherLiveData

    private val _loadingStateLiveData = MutableLiveData<LoadingState>()
    val loadingStateLiveData: LiveData<LoadingState>
        get() = _loadingStateLiveData
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }


    fun getDailyWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequest) {
        compositeDisposable.add(weatherApiService.getDailyWeatherFromNetwork(weatherRequest.id, weatherRequest.apiKey,
        weatherRequest.language, weatherRequest.details, weatherRequest.metric)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { dailyData ->
                            Log.d(TAG, "onSuccess: $dailyData")
                            //setDailyWeatherData(dailyData)
                            _dailyWeatherLiveData.postValue(dailyData)
                        },
                        { error ->
                            Log.d(TAG, "onError: $error")
                        }))
                    /*override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "onSubscribe: $d")
                    }

                    override fun onSuccess(t: DailyData) {
                        Log.d(TAG, "onSuccess: $t")
                        setDailyWeatherData(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: $e")
                    }*/





    }

    fun getHourlyWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequest) {
        compositeDisposable.add(weatherApiService.getHourlyWeatherFromNetwork(weatherRequest.id,
        weatherRequest.apiKey, weatherRequest.language, weatherRequest.details, weatherRequest.metric)
                .subscribeOn(Schedulers.io())
                .subscribe({ hourlyData ->
                    _hourlyWeatherLiveData.postValue(hourlyData)
                }, { error ->
                    Log.d(TAG, "getHourlyWeather: onError/ $error")
                }))
    }
                    /*override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "getHourlyWeather: onSubscribe/ $d")
                    }

                    override fun onSuccess(t: HourlyData) {
                        Log.d(TAG, "getHourlyWeather: onSuccess/ $t")
                        Resource.Success(t)
                        //setHourlyWeatherData(t)
                        _hourlyWeatherLiveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "getHourlyWeather: onError/ $e")
                    }*/
                    /*override fun onNext(t: HourlyData) {
                        Log.d(TAG, "getHourlyWeather: onNext/ $t")
                        Resource.Success(t)
                        setHourlyWeatherData(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "getHourlyWeather: onError/ $e")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "getHourlyWeather: onComplete")
                    }*/



    fun getCurrentWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequest) {
        weatherApiService.getCurrentWeather(weatherRequest.id,
                weatherRequest.apiKey, weatherRequest.language, weatherRequest.details)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<CurrentWeather>{
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "getCurrentWeather: onSubscribe/ $d")
                    }

                    override fun onSuccess(t: CurrentWeather) {
                        Log.d(TAG, "getCurrentWeather: onSuccess/ $t")
                        //setCurrentWeatherData(t)
                        _currentWeatherLiveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "getCurrentWeather: onError/ $e")
                    }



                })
    }

   /* fun getWeather(compositeDisposable: CompositeDisposable,
                   weatherRequest: WeatherRequest): Single<Weather> {
        return Single.zip(
                weatherApiService.getHourlyWeatherFromNetwork(weatherRequest.id,
                        weatherRequest.apiKey, weatherRequest.language, weatherRequest.details, weatherRequest.metric),
                weatherApiService.getDailyWeatherFromNetwork(weatherRequest.id, weatherRequest.apiKey,
                        weatherRequest.language, weatherRequest.details, weatherRequest.metric),
                weatherApiService.getCurrentWeather(weatherRequest.id,
                        weatherRequest.apiKey, weatherRequest.language, weatherRequest.details),
                Function3<HourlyData, DailyData, CurrentWeather, Weather>{
                    t1: HourlyData, t2: DailyData, t3: CurrentWeather ->

                }
        )
        /*getHourlyWeather(compositeDisposable, weatherRequest)
        getDailyWeather(compositeDisposable, weatherRequest)
        val request = weatherRequest.copy(id = weatherRequest.id, apiKey = weatherRequest.apiKey, language = weatherRequest.language,
                details = weatherRequest.details, metric = "")
        getCurrentWeather(compositeDisposable, request)*/
    }*/

    /*private fun setDailyWeatherData(dailyWeather: DailyData) {
        _dailyWeatherLiveData.value = dailyWeather
    }

    private fun setHourlyWeatherData(hourlyWeather: HourlyData) {
        _hourlyWeatherLiveData.value = hourlyWeather
    }

    private fun setCurrentWeatherData(currentWeather: CurrentWeather) {
        _currentWeatherLiveData.value = currentWeather
    }*/


}