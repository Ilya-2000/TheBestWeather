package com.impact.thebestweather.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.databinding.DailyItemBinding
import com.impact.thebestweather.models.weather.daily.DailyForecast
import com.impact.thebestweather.ui.weather.WeatherViewModel

class DailyRvAdapter(private var viewModel: WeatherViewModel): RecyclerView.Adapter<DailyRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(var dailyItemBinding: DailyItemBinding): RecyclerView.ViewHolder(dailyItemBinding.root) {
        fun bind(item: DailyForecast) {

        }
    }
}