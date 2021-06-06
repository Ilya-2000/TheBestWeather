package com.impact.thebestweather.data.repository

import com.impact.thebestweather.data.WeatherSource

class WeatherRepository(){
    companion object {
        private val TAG = "WeatherRepository"
        private val weatherSource = WeatherSource()
        /*lateinit var dailyData: DailyData
        lateinit var hourlyData: HourlyData
        lateinit var currentWeather: CurrentWeather*/

        /*private fun getDailyWeather(compositeDisposable: CompositeDisposable,
                                    weatherRequest: WeatherRequestData): DailyData {
            weatherSource.getDailyWeather(compositeDisposable, weatherRequest)
            Log.d(TAG,"getWeather")

            return weatherSource.dailyWeatherLiveData.value!!
        }

        private fun getHourlyWeather(compositeDisposable: CompositeDisposable,
                                     weatherRequest: WeatherRequestData): HourlyData {
            weatherSource.getHourlyWeather(compositeDisposable, weatherRequest)
            return weatherSource.hourlyWeatherLiveData.value!!
        }

        private fun getCurrentWeather(compositeDisposable: CompositeDisposable,
                                      weatherRequest: WeatherRequestData): CurrentWeather {
            weatherSource.getCurrentWeather(compositeDisposable, weatherRequest)
            return weatherSource.currentWeatherLiveData.value!!
        }*/

         /*fun getWeather(compositeDisposable: CompositeDisposable, weatherRequest: WeatherRequestData): Weather {
            weatherSource.getWeather(compositeDisposable, weatherRequest)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weather ->

                    },{ e ->

                    })

        }*/


    }


}