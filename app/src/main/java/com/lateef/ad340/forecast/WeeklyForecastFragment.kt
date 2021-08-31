package com.lateef.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lateef.ad340.*
import com.lateef.ad340.details.ForecastDetailsFragment
import com.lateef.ad340.details.TempDisplaySettingsManager


class WeeklyForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager
    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository

   /* private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())
        val zipcode = arguments?.getString(KEY_ZIPCODE)?: ""
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)

        locationEntryButton.setOnClickListener {
            //appNavigator.navigateToLocationEntry()
            showLocationEntry()
        }

        val forecastList: RecyclerView = view.findViewById(R.id.forcastlist)

        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter (tempDisplaySettingsManager){ forecast ->

            showForecastDetails(forecast)
        }

        // setting the adapter into recyclerview
        forecastList.adapter = dailyForecastAdapter
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        forecastList.addItemDecoration(divider)

        //passing in observe
        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems->
            dailyForecastAdapter.submitList(forecastItems)
        }
        //adding observe to this repository, the observe will let us know of any update
        //each observe method require lifecycle owner
        forecastRepository.wForecast.observe(requireActivity(), weeklyForecastObserver)

       // forecastRepository.LoadForecast(zipcode)

        //initiallizing locationRepository
        locationRepository = LocationRepository(requireContext())
        //observe changes to the location by creating observer
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when (savedLocation){
                is Location.Zipcode -> forecastRepository.loadWeeklyForecast(savedLocation.zipcode)
            }
        }
        locationRepository.saveLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return view
    }
    private fun showLocationEntry(){
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast){
        //appNavigator.navigateToForecastDetails(forecast)
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(forecast.temp, forecast.description)
        findNavController().navigate(action)

    }
    companion object{
        const val KEY_ZIPCODE = "key_zipcode"

        fun newinstance(zipcode: String): WeeklyForecastFragment{
            val fragment = WeeklyForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }
}