package com.impact.thebestweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.databinding.HourlyWeatherItemBinding

class HourlyRvAdapter(): RecyclerView.Adapter<HourlyRvAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: HourlyWeatherItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context),
                        R.layout.hourly_weather_item, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 24



    inner class ViewHolder(itemView: HourlyWeatherItemBinding): RecyclerView.ViewHolder(itemView.root) {

    }
}