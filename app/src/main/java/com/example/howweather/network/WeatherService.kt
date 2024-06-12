package com.example.howweather.network

import com.example.howweather.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

// 배포시에는 안전하게 보완 적용 필요
// 공공 데이터 포탈에서 발급 받은 자신만의 API키를 입력해 주세요.
private const val API_KEY =
    "c1ISMi%2BObOku4US5IhRtwjn5ZYQ3INLTkaqAYaOhkdArfvUx2HzAN%2Bj4w86CivGR7tUT9UJe1C9q5wEZXZW2bw%3D%3D"

interface WeatherService {

    @GET("getVilageFcst?serviceKey=$API_KEY")
    suspend fun getWeather(
        @Query("base_date") baseDate: Int,
        @Query("base_time") baseTime: String = "0500",
        @Query("nx") nx: String = "60",
        @Query("ny") ny: String = "121",
        @Query("numOfRows") numOfRows: Int = 12,
        @Query("pageNo") pageNo: Int = 1,
        @Query("dataType") dataType: String = "JSON"
    ): WeatherModel
}