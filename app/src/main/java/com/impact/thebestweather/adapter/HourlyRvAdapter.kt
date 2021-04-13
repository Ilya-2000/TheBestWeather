package com.impact.thebestweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.databinding.HourlyWeatherItemBinding
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.models.weather.hourly.HourlyDataItem
import com.impact.thebestweather.ui.weather.WeatherViewModel

class HourlyRvAdapter(private var viewModel: WeatherViewModel): RecyclerView.Adapter<HourlyRvAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: HourlyWeatherItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context),
                        R.layout.hourly_weather_item, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = viewModel.hourlyWeatherLiveData.value?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = 12



    inner class ViewHolder(var binding: HourlyWeatherItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HourlyDataItem) = with(binding) {
            binding.hourly = item
            binding.executePendingBindings()
        }

    }
}