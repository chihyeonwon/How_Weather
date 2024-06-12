package com.example.howweather.model


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
}