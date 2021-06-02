package com.impact.thebestweather.ui.city

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.impact.thebestweather.R
import com.impact.thebestweather.data.CitySource
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.models.location.LocationItem
import com.impact.thebestweather.models.location.LocationRequest
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.network.CityApiService
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import com.impact.thebestweather.utils.RxSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit

class CityViewModel : ViewModel() {
    private val TAG = "CityViewModel"
    private val compositeDisposable = CompositeDisposable()
    private lateinit var disposable: Disposable
    lateinit var citySource: CitySource
    private val cityApiService by lazy {
        CityApiService.create()
    }

    private val _cityListLiveData = MutableLiveData<Location>()
    val cityListLiveData: LiveData<Location>
        get() = _cityListLiveData

    private val _loadLiveData = MutableLiveData<LoadingState>()
    val loadLiveData: LiveData<LoadingState>
        get() = _loadLiveData

    private val _selectedCityLiveData = MutableLiveData<LocationItem>()
    val selectedCityLiveData: LiveData<LocationItem>
        get() = _selectedCityLiveData
    private val _weatherRequestLiveData = MutableLiveData<WeatherRequest>()
    val weatherRequestLiveData: LiveData<WeatherRequest>
        get() = _weatherRequestLiveData

    fun observeSearchView(searchView: SearchView) {
        citySource = CitySource()
        _loadLiveData.value = LoadingState.LOADING
        disposable = RxSearchView.observeSearchView(searchView)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, it.toString())
                    if (it != null) {
                        searchCities(it)
                    }
                    _loadLiveData.value = LoadingState.LOADED

                },
                {
                    Log.d(TAG, it.toString())
                    _loadLiveData.value = LoadingState.error(it.message)
                }
            )

    }


    private fun searchCities(query: String) {
        try {
            compositeDisposable.add(cityApiService.searchCity(
                Constant.API_KEY,
                query, "en", "false"
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _cityListLiveData.postValue(it)
                    },
                    {
                        Log.d(TAG, it.message.toString())
                        _loadLiveData.postValue(LoadingState.error(it.message))
                    }
                ))
        }catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun setSelectedCity(position: Int, navController: NavController) {
        val bundle = bundleOf(
            "keyCity" to cityListLiveData.value?.get(position)?.Key,
            "nameCity" to cityListLiveData.value?.get(position)?.EnglishName)
        Log.d(TAG, selectedCityLiveData.value.toString())
        navController.navigate(R.id.action_navigation_city_to_navigation_home, bundle)
    }


}



