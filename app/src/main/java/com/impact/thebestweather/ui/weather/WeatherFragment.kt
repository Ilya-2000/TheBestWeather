package com.impact.thebestweather.ui.weather

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.impact.thebestweather.R
import com.impact.thebestweather.adapter.DailyRvAdapter
import com.impact.thebestweather.adapter.HourlyRvAdapter
import com.impact.thebestweather.databinding.WeatherFragmentBinding
import com.impact.thebestweather.utils.LoadingState
import dagger.hilt.EntryPoint
import java.text.SimpleDateFormat
import java.util.*


@EntryPoint
class WeatherFragment : Fragment() {

    private val TAG = "WeatherFragment"
    //private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var navController: NavController

    /*private val weatherViewModel: WeatherViewModel by viewModels {
        BooksViewModel.BooksViewModelFactory(
            ((requireActivity().application) as ).getBooksUseCase,
            ((requireActivity().application) as CleanArchitectureBlueprintsApplication).getBookmarksUseCase,

        )
    }*/
    private val weatherViewModel : WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        /*weatherViewModel =
                ViewModelProvider(this).get(WeatherViewModel::class.java)*/
        weatherViewModel.getWeatherRequest(navController)?.let {
            weatherViewModel.getWeather(it)
        }
        val binding: WeatherFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        weatherViewModel.lastCityLiveData.observe(viewLifecycleOwner, Observer {
            binding.cityWeatherText.text = it
        })
        binding.bgWeatherLayout.visibility = View.VISIBLE
        binding.hourlyWeatherRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.dailyWeatherRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        weatherViewModel.loadingState.observe(viewLifecycleOwner, Observer { it ->
            when(it.status) {
                LoadingState.Status.FAILED -> {
                    binding.loadingMessageText.text = it.msg
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                    Log.d(TAG, "it: $it")
                    Glide.with(this)
                            .load(R.drawable.dead_emoji)
                            .into(binding.imgLoadBg)
                }
                LoadingState.Status.RUNNING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    binding.loadingMessageText.text = it.msg
                    Log.d(TAG, "it: $it")
                    Glide.with(this)
                            .load(R.drawable.happy_emoji)
                            .into(binding.imgLoadBg)
                }
                LoadingState.Status.SUCCESS -> {
                    binding.bgWeatherLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    //val adapter = HourlyRvAdapter(weatherViewModel)
                    //val dailyAdapter = DailyRvAdapter(weatherViewModel)
                    //binding.hourlyWeatherRv.adapter = adapter
                    //binding.dailyWeatherRv.adapter = dailyAdapter
                    //adapter.notifyDataSetChanged()
                    //dailyAdapter.notifyDataSetChanged()
                    Log.d(TAG, "it: $it")
                    Log.d(TAG, "weather: ${weatherViewModel.weatherLiveData.value}")

                    weatherViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
                        binding.currentWeatherDesc = weatherViewModel.currentWeatherLiveData.value?.get(0)?.WeatherText
                        if (checkTypeValues()) {
                            binding.currentWeatherTemp = weatherViewModel.currentWeatherLiveData
                                .value?.get(0)?.Temperature?.Metric?.Value.toString()
                        } else {
                            binding.currentWeatherTemp = weatherViewModel.currentWeatherLiveData
                                .value?.get(0)?.Temperature?.Imperial?.Value.toString()
                        }
                    })

                    val sdf = SimpleDateFormat("dd/M/yyyy")
                    val currentDate = sdf.format(Date())
                    binding.dateWeatherText.text = currentDate
                }
            }

        })
        return binding.root
    }

    private fun checkTypeValues(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val mValues = sharedPreferences.getString("metricValues", "")
        return mValues.equals("metric")
    }



}