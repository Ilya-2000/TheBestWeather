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
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
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
        return binding.root
    }

    private fun observeSearchView(searchView: SearchView) {
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String?): Boolean {
                    newText?.let { subscriber.onNext(it) }
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let { subscriber.onNext(it) }
                    return false
                }

            })
        })
                .map { text -> text.toLowerCase().trim() }
                .debounce(100, TimeUnit.MILLISECONDS)
                .distinct()
                .subscribe { text -> Log.d(TAG, "text: $text") }
    }
}