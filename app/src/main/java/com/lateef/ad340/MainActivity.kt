package com.lateef.ad340

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lateef.ad340.details.TempDisplaySettingsManager
import com.lateef.ad340.forecast.CurrentForecastFragmentDirections
import androidx.appcompat.widget.Toolbar



class MainActivity : AppCompatActivity() {


    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingsManager = TempDisplaySettingsManager(this)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        findViewById<Toolbar>(R.id.toolbar).setTitle(R.string.app_name)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //handle item selection
        return when(item.itemId){
            R.id.tempDisplaySettings ->{
                showTempDisplayDialog(this, tempDisplaySettingsManager)
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

   /* override fun navigateToCurrentForecast(zipcode: String) {

        //val action = LocationEntryFragmentDirections.actionLocationEntryFragmentToCurrentForecastFragment()
        //findNavController(R.id.nav_host_fragment).navigate(action)

    }

    override fun navigateToLocationEntry() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController(R.id.nav_host_fragment).navigate(action)

    }

    override fun navigateToForecastDetails(forecast: DailyForecast) {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp, forecast.description)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }*/


}
