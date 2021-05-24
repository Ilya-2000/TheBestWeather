package com.impact.thebestweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.impact.thebestweather.R
import com.impact.thebestweather.databinding.СityCardBinding
import com.impact.thebestweather.models.location.LocationItem
import com.impact.thebestweather.ui.city.CityViewModel
import java.util.zip.Inflater

class CityListRvAdapter(val viewModel: CityViewModel, val navController: NavController): RecyclerView.Adapter<CityListRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : СityCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.favorite_city_card,
            parent,
            false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = viewModel.cityListLiveData.value?.get(position)
        if (item != null) {
            holder.bind(item)
        }
        holder.itemView.setOnClickListener {
            viewModel.setSelectedCity(position, navController)
        }
    }

    override fun getItemCount(): Int {
        if (viewModel.cityListLiveData.value?.size != null) {
            return viewModel.cityListLiveData.value!!.size
        } else {
            return 0
        }
    }


    inner class ViewHolder(private val cityCardBinding: СityCardBinding) : RecyclerView.ViewHolder(cityCardBinding.root) {
        fun bind(item: LocationItem) = with(cityCardBinding){
            cityCardBinding.cityItem = item
            cityCardBinding.executePendingBindings()
        }
    }


}