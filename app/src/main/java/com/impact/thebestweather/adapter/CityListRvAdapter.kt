package com.impact.thebestweather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.databinding.–°ityCardBinding
import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.models.location.LocationItem


class CityListRvAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<CityListRvAdapter.ViewHolder>() {
    private val cityList = arrayListOf<LocationItem>()

    interface OnItemClickListener {
        fun onItemClick(locationItem: LocationItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : –°ityCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.favorite_city_card,
            parent,
            false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cityList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onItemClick(cityList[position])
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }


    inner class ViewHolder(private val cityCardBinding: –°ityCardBinding) : RecyclerView.ViewHolder(cityCardBinding.root) {
        fun bind(item: LocationItem) = with(cityCardBinding){
            cityCardBinding.cityItem = item
            cityCardBinding.executePendingBindings()
        }
    }

    fun addData(location: Location) {
        cityList.clear()
        cityList.addAll(location)
    }




}