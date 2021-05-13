package com.impact.thebestweather.ui.city

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.impact.thebestweather.R
import com.impact.thebestweather.databinding.CityFragmentBinding
import com.impact.thebestweather.utils.RxSearchView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CityFragment : Fragment() {
    private val TAG = "CityFragment"

    private lateinit var cityViewModel: CityViewModel
    private lateinit var disposable: Disposable
    var text: String = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cityViewModel =
                ViewModelProvider(this).get(CityViewModel::class.java)
        val binding: CityFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_city, container, false)
        observeSearchView(binding.citySearchView)
        return binding.root
    }



    private fun observeSearchView(searchView: SearchView) {
        disposable = RxSearchView.observeSearchView(searchView)
            .debounce(800, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, it.toString())
                },
                {
                    Log.d(TAG, it.toString())
                }
            )

    }


}