package com.example.howweather.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.howweather.R
import com.example.howweather.data.WeatherData
import com.example.howweather.databinding.ItemLayoutBinding

class WeatherItemListAdapter :
    ListAdapter<WeatherData, WeatherItemListAdapter.ItemViewHolder>(object :
        DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val timeView = binding.timeTv
        private val temperatureView = binding.tempTv
        private val weatherStatusView = binding.weatherIv
        private val rainStatusView = binding.rainIv
        fun bind(item: WeatherData?) {
            val res = timeView.context.resources
            timeView.text = item?.time?.toTimeFormat()
            temperatureView.text = res.getString(R.string.temp_text, item?.temperature)
            weatherStatusView.setImageResource(item?.skyStatus?.icon ?: R.drawable.ic_sunny)
            rainStatusView.setImageResource(item?.rainState?.icon ?: R.drawable.ic_sunny)
        }

        private fun String.toTimeFormat(): String {
            return if (this.length == 4) "${this.substring(0, 2)}:${this.substring(2)}" else this
        }
    }
}