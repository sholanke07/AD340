package com.lateef.ad340.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.lateef.ad340.LocationRepository
import com.lateef.ad340.Location
import com.lateef.ad340.R


class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository

   /* private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        locationRepository = LocationRepository(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEdit)
        val btn: Button = view.findViewById(R.id.btn)

        val zipcode: String = zipcodeEditText.text.toString()

       btn.setOnClickListener {
           if (zipcode.length == 5) {
              // appNavigator.navigateToCurrentForecast(zipcode)

                Toast.makeText(requireContext(), "nothing", Toast.LENGTH_SHORT).show()

            } else {
                locationRepository.saveLocation(Location.Zipcode(zipcode))

               findNavController().navigateUp()
                // appNavigator.navigateToCurrentForecast(zipcode)

           }
        }

        return view
    }


}