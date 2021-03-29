package com.impact.thebestweather.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.impact.thebestweather.R

class CityFragment : Fragment() {

    private lateinit var cityViewModel: CityViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cityViewModel =
                ViewModelProvider(this).get(CityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_city, container, false)
        return root
    }
}