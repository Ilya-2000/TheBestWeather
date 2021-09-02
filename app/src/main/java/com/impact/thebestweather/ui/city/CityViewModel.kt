package com.impact.thebestweather.ui.city

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
import com.impact.thebestweather.models.weather.WeatherRequestData
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


}



