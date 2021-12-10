package com.impact.thebestweather.ui.city

import android.util.Log
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.impact.thebestweather.R
import com.impact.thebestweather.models.LocationSharedData
import com.impact.thebestweather.models.location.CityRequest
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.models.location.LocationItem
import com.impact.thebestweather.network.CityApiService
import com.impact.thebestweather.usecases.GetCitiesUseCase
import com.impact.thebestweather.usecases.GetSelectedCityUseCase
import com.impact.thebestweather.usecases.SetSelectCityUseCase
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import com.impact.thebestweather.utils.RxSearchView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val setSelectCityUseCase: SetSelectCityUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getSelectedCityUseCase: GetSelectedCityUseCase
) : ViewModel() {
    private val TAG = "CityViewModel"
    private val compositeDisposable = CompositeDisposable()
    private lateinit var disposable: Disposable

    private val _cityListLiveData = MutableLiveData<Location>()
    val cityListLiveData: LiveData<Location>
        get() = _cityListLiveData

    private val _loadLiveData = MutableLiveData<LoadingState>()
    val loadLiveData: LiveData<LoadingState>
        get() = _loadLiveData

    private val _selectedCityLiveData = MutableLiveData<LocationSharedData>()
    val selectedCityLiveData: LiveData<LocationSharedData>
        get() = _selectedCityLiveData


    fun observeSearchView(searchView: SearchView) {
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
            compositeDisposable.add(getCitiesUseCase.execute(
                CityRequest(
                    Constant.API_KEY,
                    query,
                    language = "en",
                    "false"
                )
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

    fun setSelectedCity(locationItem: LocationItem) {
        setSelectCityUseCase.execute(locationItem)
    }




}



