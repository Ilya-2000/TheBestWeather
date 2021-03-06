package com.impact.thebestweather.ui.city

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.adapter.CityListRvAdapter
import com.impact.thebestweather.databinding.CityFragmentBinding
import com.impact.thebestweather.models.location.LocationItem
import com.impact.thebestweather.utils.LoadingState
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
@AndroidEntryPoint
class CityFragment : Fragment(), CityListRvAdapter.OnItemClickListener {
    private val TAG = "CityFragment"
    private lateinit var navController: NavController
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityListRvAdapter

    private val cityViewModel: CityViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: CityFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_city, container, false)
        cityViewModel.observeSearchView(binding.citySearchView)
        recyclerView = binding.cityListRv
        navController = findNavController()

        cityViewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                LoadingState.Status.RUNNING -> {
                    Log.d(TAG, "Status: $it")
                    //binding.cityProgressBar.visibility = View.VISIBLE
                    binding.messageCityText.visibility = View.VISIBLE
                    binding.messageCityText.text = it.msg
                }
                LoadingState.Status.FAILED -> {
                    Log.d(TAG, "Status: $it")
                    //binding.cityProgressBar.visibility = View.GONE
                    binding.messageCityText.visibility = View.VISIBLE
                    binding.messageCityText.text = it.msg
                }
                LoadingState.Status.SUCCESS -> {
                    //binding.cityProgressBar.visibility = View.GONE
                    binding.messageCityText.visibility = View.GONE
                    Log.d(TAG, "Status: $it")
                    cityViewModel.cityListLiveData.observe(viewLifecycleOwner, Observer {data ->
                        Log.d(TAG, "BRUH $it")
                        adapter.addData(data)
                        adapter.notifyDataSetChanged()
                    })
                }
            }

        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

    }

    override fun onItemClick(locationItem: LocationItem) {
        cityViewModel.setSelectedCity(locationItem)
        navController.navigate(R.id.action_navigation_city_to_navigation_home)
    }

    private fun setupRecyclerView() {
        adapter = CityListRvAdapter(this)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }


}