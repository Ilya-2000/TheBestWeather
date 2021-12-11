package com.impact.thebestweather.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.databinding.DailyItemBinding
import com.impact.thebestweather.databinding.HourlyWeatherItemBinding
import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.daily.DailyForecast
import com.impact.thebestweather.ui.weather.WeatherViewModel

class DailyRvAdapter(): RecyclerView.Adapter<DailyRvAdapter.ViewHolder>() {
    private val dailyDataList = arrayListOf<DailyForecast>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: DailyItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.daily_weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dailyDataList[position]
        holder.bind(item)

    }

    override fun getItemCount() = dailyDataList.size

    inner class ViewHolder(var dailyItemBinding: DailyItemBinding): RecyclerView.ViewHolder(dailyItemBinding.root) {
        fun bind(item: DailyForecast) = with(dailyItemBinding) {
            val localDate = item.Date
            dailyItemBinding.date = cutDate(localDate)
            dailyItemBinding.dailyItem = item
            dailyItemBinding.executePendingBindings()
        }
    }

    private fun cutDate(localDate: String): String {
        return localDate.substring(0,10)
    }

    fun addData(data: List<DailyForecast>) {
        dailyDataList.clear()
        dailyDataList.addAll(data)
    }

}