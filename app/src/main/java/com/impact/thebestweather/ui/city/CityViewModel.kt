package com.impact.thebestweather.ui.city

import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.impact.thebestweather.data.CitySource
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.models.location.LocationRequest
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


    fun getCityList(citySource: CitySource) {
        _cityListLiveData.postValue(citySource.cityListLiveData.value)
        Log.d(TAG, "getCityList ${citySource.cityListLiveData.value}")
    }


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
                    _cityListLiveData.postValue(citySource.searchCity(compositeDisposable,
                        LocationRequest(Constant.API_KEY, it, "en", "false")).value)
                    _loadLiveData.value = LoadingState.LOADED
                },
                {
                    Log.d(TAG, it.toString())
                    _loadLiveData.value = LoadingState.error(it.message)
                }
            )

    }



}