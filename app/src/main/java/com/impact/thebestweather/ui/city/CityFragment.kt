package com.impact.thebestweather.ui.city

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.data.CitySource
import com.impact.thebestweather.databinding.CityFragmentBinding
import com.impact.thebestweather.models.location.LocationRequest
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import com.impact.thebestweather.utils.RxSearchView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CityFragment : Fragment() {
    private val TAG = "CityFragment"

    private lateinit var cityViewModel: CityViewModel
    private lateinit var disposable: Disposable
    var text: String = ""
    private val compositeDisposable = CompositeDisposable()
    lateinit var citySource: CitySource
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cityViewModel =
                ViewModelProvider(this).get(CityViewModel::class.java)
        val binding: CityFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_city, container, false)
        cityViewModel.observeSearchView(binding.citySearchView)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityViewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                LoadingState.Status.RUNNING -> {
                    Log.d(TAG, "Status: $it")
                }
                LoadingState.Status.FAILED -> {
                    Log.d(TAG, "Status: $it")
                }
                LoadingState.Status.SUCCESS -> {
                    Log.d(TAG, "Status: $it")
                    cityViewModel.cityListLiveData.observe(viewLifecycleOwner, Observer {
                        Log.d(TAG, "BRUH $it")
                    })
                }
            }

        })
    }




    /*private fun observeSearchView(searchView: SearchView) {
        citySource = CitySource()
        disposable = RxSearchView.observeSearchView(searchView)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, it.toString())
                    //cityViewModel.searchCity(it)
                    citySource.searchCity(compositeDisposable,
                        LocationRequest(Constant.API_KEY, it, "en", "false")
                    )
                },
                {
                    Log.d(TAG, it.toString())
                }
            )

    }*/


}