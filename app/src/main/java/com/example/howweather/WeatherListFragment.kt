package com.example.howweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.howweather.databinding.FragmentWeatherListBinding
import com.example.howweather.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherListFragment : Fragment() {
    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!
    private val listAdapter by lazy { WeatherItemListAdapter() }
    private val currentDate by lazy { SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date()) }
    private val viewModel by activityViewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            weatherList.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(requireContext())
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
                    getDrawable(requireContext(), R.drawable.list_divider)?.let { setDrawable(it) }
                }.also {
                    addItemDecoration(it)
                }
            }
            viewModel.regionText.observe(viewLifecycleOwner) {
                regionText.text = it
                fetchWeatherData()
            }
            viewModel.weatherList.observe(viewLifecycleOwner) {
                listAdapter.submitList(it)
                val currentWeather = it.first()
                weatherStatusIv.setImageResource(currentWeather.skyStatus.colorIcon)
                mainWeatherText.text = currentWeather.skyStatus.text
                mainTemperTv.text = currentWeather.temperature
            }
        }
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModel.getWeatherList(currentDate)
    }
}