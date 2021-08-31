package com.lateef.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lateef.ad340.details.TempDisplaySettingsManager


class DailyForecastViewHolder(view: View, private val tempDisplaySettingsManager: TempDisplaySettingsManager): RecyclerView.ViewHolder(view){

    private val tempText: TextView = view.findViewById(R.id.temptext)
    private val descriptionText: TextView = view.findViewById(R.id.descriptionText)

    fun bind(dailyForecast: DailyForecast){
        tempText.text = formatTempForDisplay(dailyForecast.temp, tempDisplaySettingsManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.description
    }

}
class DailyForecastAdapter(
    private val tempDisplaySettingsManager: TempDisplaySettingsManager,
    private val clickHandler: (DailyForecast) -> Unit
): ListAdapter<DailyForecast, DailyForecastViewHolder>(DIFF_CONFIG) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingsManager)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            clickHandler(getItem(position))
        }
    }

    companion object{
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>(){
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem

            }

        }
    }
}