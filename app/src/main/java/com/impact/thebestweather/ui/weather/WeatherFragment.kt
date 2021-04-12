package com.impact.thebestweather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.adapter.HourlyRvAdapter

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
                ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.getDailyWeather()
        weatherViewModel.getHourlyWeather()
        val root = inflater.inflate(R.layout.fragment_weather, container, false)
        val hourlyRv = root.findViewById<RecyclerView>(R.id.hourly_weather_rv)
        hourlyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weatherViewModel.hourlyWeatherLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = HourlyRvAdapter(weatherViewModel)
            hourlyRv.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        return root
    }
}