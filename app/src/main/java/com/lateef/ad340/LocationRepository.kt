package com.lateef.ad340

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


sealed class Location{
    data class Zipcode(val zipcode: String): Location()
}

private const val KEY_ZIPCODE = "zip_code"

class LocationRepository(context: Context) {
    private val prefrences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _saveLocation: MutableLiveData<Location> = MutableLiveData()
    val saveLocation: LiveData<Location> = _saveLocation

    //when location Repository is created the block of code will be run
    //anytime shared preference changes it will notified
    init {
        prefrences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key != KEY_ZIPCODE) return@registerOnSharedPreferenceChangeListener

           broadcastSavedZipcode()
        }

        //where is to notify our observer what the current value is
        broadcastSavedZipcode()
    }

    //to save the location value
    fun saveLocation(location: Location) {
        when (location) {
            //anytime we called save location it will save it in shared prefrences
            is Location.Zipcode -> prefrences.edit().putString(KEY_ZIPCODE, location.zipcode).apply()

        }
    }

    //we created this method to observe what the current value is
    private fun broadcastSavedZipcode() {
        val zipcode = prefrences.getString(KEY_ZIPCODE, "")
        if (zipcode != null && zipcode.isNotBlank()) {
            _saveLocation.value = Location.Zipcode(zipcode)

        }

    }
}