package com.lateef.ad340.details

import android.content.Context

enum class TempDisplaySetting {
 Fahrenheit, Celsius
}

// sharedpreference
class TempDisplaySettingsManager(context: Context){
    private val preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    //to store data in sharedpreference
    fun updateSetting(setting: TempDisplaySetting){
        preferences.edit().putString("key_temp_display", setting.name).commit()
    }
    //to get data from sharedpreference
    fun getTempDisplaySetting(): TempDisplaySetting{
        val settingValue = preferences.getString("key_temp_display", TempDisplaySetting.Fahrenheit.name)?: TempDisplaySetting.Fahrenheit.name
        return TempDisplaySetting.valueOf(settingValue)
    }
}