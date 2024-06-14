package com.example.howweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.howweather.databinding.FragmentWeatherHomeBinding
import com.example.howweather.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherHomeFragment : Fragment() {

    private var _binding: FragmentWeatherHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val currentDate by lazy {
        SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWeatherHomeBinding.inflate(inflater, container, false)
        with(binding) {
            val repository = WeatherRepository()
            lifecycleScope.launch {
                val model = repository.getWeather(currentDate, "서울특별시")
                val data = model.toWeatherData()
                mainWeatherText.text = data.skyStatus.text
                mainTemperTv.text = data.temperature
                mainRainTv.text = data.rainState.value.toString()
                mainWaterTv.text = data.humidity
                mainWindTv.text = data.windSpeed
                mainRainPercentTv.text = getString(R.string.rain_percent, data.rainPercent)
                rainStatusIv.setImageResource(data.rainState.icon)
                weatherStatusIv.setImageResource(data.skyStatus.colorIcon)
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}