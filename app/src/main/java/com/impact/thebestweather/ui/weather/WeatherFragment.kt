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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.impact.thebestweather.R
import com.impact.thebestweather.adapter.DailyRvAdapter
import com.impact.thebestweather.adapter.HourlyRvAdapter
import com.impact.thebestweather.databinding.WeatherFragmentBinding
import com.impact.thebestweather.models.weather.WeatherRequest
import com.impact.thebestweather.utils.Constant
import com.impact.thebestweather.utils.LoadingState
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater
private const val ARG_PARAM1 = "keyCity"
private const val ARG_PARAM2 = "nameCity"

class WeatherFragment : Fragment() {
    private var cityKey: String? = null
    private var city: String? = null
    private val TAG = "WeatherFragment"
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityKey = it.getString(ARG_PARAM1)
            city = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
                ViewModelProvider(this).get(WeatherViewModel::class.java)
        getWeatherRequest()?.let { weatherViewModel.getWeather(it) }
        val binding: WeatherFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        binding.cityWeatherText.text = city
        binding.bgWeatherLayout.visibility = View.VISIBLE
        binding.hourlyWeatherRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.dailyWeatherRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        weatherViewModel.loadingState.observe(viewLifecycleOwner, Observer { it ->
            when(it.status) {
                LoadingState.Status.FAILED -> {
                    binding.loadingMessageText.text = it.msg
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                    Log.d(TAG, "it: $it")
                    Glide.with(this)
                            .load(R.drawable.dead_emoji)
                            .into(binding.imgLoadBg)
                }
                LoadingState.Status.RUNNING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    binding.loadingMessageText.text = it.msg
                    Log.d(TAG, "it: $it")
                    Glide.with(this)
                            .load(R.drawable.happy_emoji)
                            .into(binding.imgLoadBg)
                }
                LoadingState.Status.SUCCESS -> {
                    binding.bgWeatherLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    val adapter = HourlyRvAdapter(weatherViewModel)
                    val dailyAdapter = DailyRvAdapter(weatherViewModel)
                    binding.hourlyWeatherRv.adapter = adapter
                    binding.dailyWeatherRv.adapter = dailyAdapter
                    adapter.notifyDataSetChanged()
                    dailyAdapter.notifyDataSetChanged()
                    Log.d(TAG, "it: $it")
                    Log.d(TAG, "weather: ${weatherViewModel.dailyWeatherLiveData.value}")

                    weatherViewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {

                        binding.currentWeather = weatherViewModel.currentWeatherLiveData.value?.get(0)
                    })

                    val sdf = SimpleDateFormat("dd/M/yyyy")
                    val currentDate = sdf.format(Date())
                    binding.dateWeatherText.text = currentDate

                    /*Glide.with(this)
                        .load(weatherViewModel.currentWeatherLiveData.value?.get(0)?.WeatherIcon)
                        .into(currentWeatherIcon)*/
                }
            }

        })




        return binding.root
    }

    private fun getWeatherRequest(): WeatherRequest? {
        val stringBuilder = StringBuilder()
        val boolean: Boolean? = null
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        Log.d(TAG, "defaultCity: $boolean")
        if (sharedPreferences.getBoolean("defaultCity", boolean == true)) {
            return WeatherRequest(
                stringBuilder.append(sharedPreferences.getString("cityKey", "")).toString(),
                Constant.API_KEY,
            "en",
            "false",
                stringBuilder.append(sharedPreferences.getString("metricValues", "")).toString()
            )
        } else if (!sharedPreferences.getBoolean("defaultCity", boolean == true)) {
            return cityKey?.let {
                WeatherRequest(
                    it,
                    Constant.API_KEY,
                    "en",
                    "false",
                    stringBuilder.append(sharedPreferences.getString("metricValues", "")).toString()
                )
            }
        }
        return null
    }

    /*companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}