package com.impact.thebestweather.ui.weather

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.impact.thebestweather.R
import com.impact.thebestweather.adapter.HourlyRvAdapter
import com.impact.thebestweather.utils.LoadingState

class WeatherFragment : Fragment() {
    private val TAG = "WeatherFragment"
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
                ViewModelProvider(this).get(WeatherViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_weather, container, false)
        val hourlyRv = root.findViewById<RecyclerView>(R.id.hourly_weather_rv)
        val background = root.findViewById<FrameLayout>(R.id.bg_weather_layout)
        val currentTempText = root.findViewById<TextView>(R.id.current_temp_weather)
        val currentConditionsText = root.findViewById<TextView>(R.id.current_weather_condition_text)
        val currentWeatherIcon = root.findViewById<ImageView>(R.id.current_weather_icon)
        background.visibility = View.VISIBLE
        hourlyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weatherViewModel.loadingState.observe(viewLifecycleOwner, Observer { it ->
            when(it.status) {
                LoadingState.Status.FAILED -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                    Log.d(TAG, "it: $it")
                }
                LoadingState.Status.RUNNING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "it: $it")
                }
                LoadingState.Status.SUCCESS -> {
                    background.visibility = View.GONE
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    val adapter = HourlyRvAdapter(weatherViewModel)
                    hourlyRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "it: $it")
                    currentTempText.text = weatherViewModel.currentWeatherLiveData.value?.get(0)?.Temperature?.Metric?.Value.toString()
                    currentConditionsText.text = weatherViewModel.currentWeatherLiveData.value?.get(0)?.WeatherText.toString()
                    Glide.with(this)
                        .load(weatherViewModel.currentWeatherLiveData.value?.get(0)?.WeatherIcon)
                        .into(currentWeatherIcon)
                }
            }

        })




        return root
    }
}