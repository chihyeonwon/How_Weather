package com.example.howweather.model


import android.util.Log
import com.example.howweather.RainStatus
import com.example.howweather.SkyStatus
import com.example.howweather.WeatherData
import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("response")
    val response: Response
) {
    fun toWeatherData(): WeatherData {
        return response.body.items.item.toWeatherData()
    }

    private fun List<Item>.toWeatherData(): WeatherData {
        val items = this
        val time = items.find { it.category == "SKY" }?.fcstTime ?: "--:--"
        val skyStatus = items.find { it.category == "SKY" }?.fcstValue ?: ""
        val rainStatus = items.find { it.category == "PTY" }?.fcstValue ?: ""
        val rainPercent = items.find { it.category == "POP" }?.fcstValue ?: ""
        val rainAmount = items.find { it.category == "PCP" }?.fcstValue ?: ""
        val temp = items.find { it.category == "TMP" }?.fcstValue ?: ""
        val windSpeed = items.find { it.category == "WSD" }?.fcstValue ?: ""
        val humidity = items.find { it.category == "REH" }?.fcstValue ?: ""

        return WeatherData(
            time = time,
            skyStatus = SkyStatus.entries.firstOrNull { it.value == skyStatus.toInt() }
                ?: SkyStatus.GOOD,
            rainAmount = rainAmount,
            rainPercent = rainPercent,
            rainState = RainStatus.entries.firstOrNull { it.value == rainStatus.toInt() }
                ?: RainStatus.NONE,
            temperature = temp,
            windSpeed = windSpeed,
            humidity = humidity
        )
    }

    fun toWeatherList(count: Int): List<WeatherData> {
        val list = mutableListOf<WeatherData>()
        val items = response.body.items.item
        val baseTime = items.first().baseTime
        var nextTime = baseTime.nextTime()
        repeat(count) {
            val subItems = items.filter { it.fcstTime == nextTime }.toList()
            val weatherData = subItems.toWeatherData()
            Log.d("WeatherData", "time: $nextTime, data: $weatherData")
            list.add(weatherData)
            nextTime = nextTime.nextTime()
        }
        return list
    }

    private fun String.nextTime(): String {
        if (this.length != 4) {
            throw IllegalArgumentException("잘못된 시간 형식")
        }

        val hour = this.substring(0, 2).toInt()
        val minute = this.substring(2, 4).toInt()

        if (hour !in 0..23 || minute !in 0..59) {
            throw IllegalArgumentException("잘못된 시간 범위")
        }

        val nextHour = if (hour == 23) 0 else hour + 1
        return "%02d%02d".format(nextHour, minute)
    }
}