package com.example.howweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            val adapter = WeatherFragmentStateAdapter(this@MainActivity)
            viewPager.adapter = adapter
            indicator.setViewPager(viewPager)
            adapter.registerAdapterDataObserver(indicator.adapterDataObserver)
            viewPager.currentItem = 1 // Weather 리스트 페이지(1번 페이지)가 제일 먼저 오도록
        }
    }
}