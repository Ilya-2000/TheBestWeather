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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.adapter.CityListRvAdapter
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
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

class CityFragment : Fragment() {
    private val TAG = "CityFragment"
    private lateinit var navController: NavController
    private lateinit var cityViewModel: CityViewModel
    var text: String = ""
    private lateinit var recyclerView: RecyclerView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}