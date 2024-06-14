package com.example.howweather.data

import com.example.howweather.R

data class WeatherData(
    val time: String = "--:--", // 시간
    val skyStatus: SkyStatus = SkyStatus.GOOD, // 하늘의 상태
    val rainPercent: String = "-", // 비 올 확률
    val rainState: RainStatus = RainStatus.NONE, // 상태별 표기
    val rainAmount: String = "-", // 비의 양
    val temperature: String = "-", // 온도
    val windSpeed: String = "-", // 바람의 속도
    val humidity: String = "-" // 습도
) {

    override fun toString(): String {
        return "날씨 : 하늘 상태 : ${skyStatus.name} " +
                "\n 강수 확률: $rainPercent \n 강수 형태: ${rainState.name}" +
                "\n 강수량 : $rainAmount \n 기온 : $temperature 도 " +
                "\n 풍속 : $windSpeed \n 습도 : $humidity"
    }
}

enum class RainStatus(val value: Int, val icon: Int) {
    NONE(0, R.drawable.ic_sunny),
    RAIN(1, R.drawable.ic_water),
    RAIN_SNOW(2, R.drawable.ic_rain_snow),
    SNOW(3, R.drawable.ic_snow),
    FALL(4, R.drawable.ic_rainy)
}

enum class SkyStatus(val value: Int, val text: String, val icon: Int, val colorIcon: Int) {
    GOOD(1, "맑음", R.drawable.ic_sunny, R.drawable.ic_color_sunny),
    CLOUDY(3, "구름 많음", R.drawable.ic_sun_cloudy, R.drawable.ic_color_sunny_cloudy),
    BAD(4, "흐림", R.drawable.ic_cloudy_2, R.drawable.ic_color_cloudy)
}