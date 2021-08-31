package com.lateef.ad340

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.lateef.ad340.details.TempDisplaySetting
import com.lateef.ad340.details.TempDisplaySettingsManager

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String{
    return when (tempDisplaySetting){
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F", temp)
        TempDisplaySetting.Celsius -> {
            //converting fahrenheit into celsius
            val temp = (temp - 32f)* (5f/9f)
            String.format("%.2f C", temp)
        }
    }
}

 fun showTempDisplayDialog(context: Context, tempDisplaySettingsManager: TempDisplaySettingsManager){
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setTitle("Choose Display Unit")
    dialogBuilder.setMessage("Choose which temperature unit to use for temperature display")
        .setPositiveButton("F"){_, _ ->
            tempDisplaySettingsManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("C"){_, _ ->
            tempDisplaySettingsManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener{
            Toast.makeText(context, "setting will dismiss", Toast.LENGTH_SHORT).show()
        }


    dialogBuilder.show()

}