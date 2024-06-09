package com.example.howweather

object WeatherDataList {
    val list = mutableListOf<WeatherData>()

    init {
        // This is dummy weather data
        with(list) {
            add(WeatherData("0500", SkyStatus.CLOUDY, "5", RainStatus.NONE, "15", "-2", "55", "23"))
            add(WeatherData("0600", SkyStatus.GOOD, "5", RainStatus.NONE, "15", "-2", "55", "23"))
            add(WeatherData("0700", SkyStatus.CLOUDY, "5", RainStatus.FALL, "55", "-2", "55", "44"))
            add(WeatherData("0800", SkyStatus.CLOUDY, "100", RainStatus.FALL, "55", "-12", "99", "43"))
            add(WeatherData("0900", SkyStatus.CLOUDY, "100", RainStatus.RAIN_SNOW, "55", "-12", "99", "73"))
            add(WeatherData("1000", SkyStatus.CLOUDY, "100", RainStatus.RAIN_SNOW, "55", "-12", "99", "73"))
            add(WeatherData("1100", SkyStatus.CLOUDY, "100", RainStatus.RAIN_SNOW, "55", "-12", "99", "73"))
            add(WeatherData("1200", SkyStatus.BAD, "500", RainStatus.SNOW, "95", "-22", "35", "93"))
            add(WeatherData("1300", SkyStatus.BAD, "500", RainStatus.SNOW, "95", "-22", "35", "93"))
            add(WeatherData("1400", SkyStatus.BAD, "500", RainStatus.SNOW, "95", "-22", "35", "93"))
        }
    }
}