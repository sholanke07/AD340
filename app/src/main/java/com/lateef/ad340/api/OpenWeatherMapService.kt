package com.lateef.ad340.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

fun createOpenWeatherMapService(): OpenWeatherMapService{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org")
        .build()

    return retrofit.create(OpenWeatherMapService::class.java)

}

interface OpenWeatherMapService {

    @GET("/data/2.5/weather")
    fun currentWeather(
        @Query("zip") zipcode: String,
    @Query("units") units: String,
        @Query("appid") apikey: String
    ): Call<CurrentWeather>
}