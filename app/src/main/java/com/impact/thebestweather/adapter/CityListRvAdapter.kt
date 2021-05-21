package com.impact.thebestweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.databinding.cityCardBinding
import com.impact.thebestweather.models.location.LocationItem
import com.impact.thebestweather.ui.city.CityViewModel
import java.util.zip.Inflater

class CityListRvAdapter(val viewModel: CityViewModel): RecyclerView.Adapter<CityListRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : cityCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.favorite_city_card,
            parent,
            false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = viewModel.cityListLiveData.value?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = 100


    inner class ViewHolder(private val cityCardBinding: cityCardBinding) : RecyclerView.ViewHolder(cityCardBinding.root) {
        fun bind(item: LocationItem) = with(cityCardBinding){
            cityCardBinding.cityItem = item
            cityCardBinding.executePendingBindings()
        }
    }


}