package com.impact.thebestweather.di

import android.content.Context
import com.impact.thebestweather.CityRepository
import com.impact.thebestweather.WeatherRepository
import com.impact.thebestweather.city.CityRemoteSource
import com.impact.thebestweather.city.CityRemoteSourceImpl
import com.impact.thebestweather.city.CityRepositoryImpl
import com.impact.thebestweather.city.LocationLocalStorageImpl
import com.impact.thebestweather.weather.WeatherRemoteSource
import com.impact.thebestweather.weather.WeatherRemoteSourceImpl
import com.impact.thebestweather.weather.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherRemoteSource: WeatherRemoteSource) : WeatherRepository
    = WeatherRepositoryImpl(weatherRemoteSource = weatherRemoteSource)

    @Provides
    @Singleton
    fun provideLocationLocalStorage(@ApplicationContext context: Context)
    = LocationLocalStorageImpl(context = context)

    @Provides
    @Singleton
    fun provideCityRepository(
        cityRemoteSource: CityRemoteSource,
        locationLocalStorageImpl: LocationLocalStorageImpl): CityRepository = CityRepositoryImpl(
        cityRemoteSource = cityRemoteSource,
        locationLocalStorage = locationLocalStorageImpl)

    @Provides
    @Singleton
    fun provideCityRemoteSource(): CityRemoteSource = CityRemoteSourceImpl()

    @Provides
    @Singleton
    fun provideWeatherRemoteSource(): WeatherRemoteSource = WeatherRemoteSourceImpl()




}