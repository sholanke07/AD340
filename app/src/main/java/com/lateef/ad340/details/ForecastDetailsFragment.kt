package com.lateef.ad340.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.lateef.ad340.R
import com.lateef.ad340.formatTempForDisplay
import com.lateef.ad340.showTempDisplayDialog

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_forecast_details, container, false)

        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())

        val tempText = layout.findViewById<TextView>(R.id.temptext)
        val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)

        tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingsManager.getTempDisplaySetting())
        descriptionText.text = args.description

        return layout
    }
}


