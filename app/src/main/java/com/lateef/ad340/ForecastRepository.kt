package com.lateef.ad340

import android.util.Log
import androidx.core.util.rangeTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lateef.ad340.api.CurrentWeather
import com.lateef.ad340.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.DateFormat
import java.util.*
import kotlin.random.Random

class ForecastRepository {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather


    // how our activity will get data from repository
    //this will get updata but cant publish the own changes
    private val _weeklyforcast = MutableLiveData<List<DailyForecast>>()
    //creating a public livedata
    val wForecast: LiveData<List<DailyForecast>> = _weeklyforcast


    //how to load the data from repository
    fun loadWeeklyForecast(zipcode: String){

        val randomValues = List(10){Random.nextFloat().rem(100) * 100}

        //creating forecastitems that will be sent to livedata
        // map function helps to convert one type to another type
        val forecastItems = randomValues.map {temp->
            //dis take in individual values and convert it to upper values of dailyForecast
            // it stand for each individual item
            DailyForecast(temp, "partly cloudy")
        }

        //sending list to liveData
        _weeklyforcast.setValue(forecastItems)

    }
    fun loadCurrentForecast(zipcode: String){
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", "apikey")
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null){
                    _currentWeather.value = weatherResponse
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading current weather", t)
            }

        })


       /* val randomtemp = Random.nextFloat().rem(100) * 100
        val forecast = DailyForecast(randomtemp, getTempDescription(randomtemp))
        _currentForecast.value = forecast*/
    }

    //to update repository without affecting other code
    private fun getTempDescription(temp: Float): String{
        return when (temp){
            in Float.MIN_VALUE.rangeTo(0f)-> "anything below 0 does not make sense"
            in 0f.rangeTo (32f) -> "too cold"
            in 32f.rangeTo(55f)-> "good"
            in 55f.rangeTo(65f)-> "better"
            in 65f.rangeTo(80f)-> "okay"
            in 80f.rangeTo(90f)-> "very good"
            in 90f.rangeTo(100f)-> "interesting"
            in 100f.rangeTo(Float.MAX_VALUE)-> "Cant say anything about this"
            else -> "no record"
        }
    }
}