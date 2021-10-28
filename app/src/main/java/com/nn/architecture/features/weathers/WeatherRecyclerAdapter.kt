package com.nn.architecture.features.weathers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nn.architecture.databinding.WeatherDailyItemBinding
import com.nn.architecture.features.weathers.model.WeatherDailyDataModel


class WeatherRecyclerAdapter(val context: Context) : ListAdapter<WeatherDailyDataModel, WeatherRecyclerAdapter.WeatherDailyViewHolder>(COMPARE_DIFF){

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDailyViewHolder{
        val binding = WeatherDailyItemBinding.inflate(layoutInflater, parent, false)
        return WeatherDailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherDailyViewHolder, position: Int) {
        if (position in 0..itemCount) {
            getItem(position)?.let {
                holder.binding.dailyData = it
            }
        }
    }

    class WeatherDailyViewHolder(val binding: WeatherDailyItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val COMPARE_DIFF = object : DiffUtil.ItemCallback<WeatherDailyDataModel>()  {
            override fun areItemsTheSame(oldItem: WeatherDailyDataModel, newItem: WeatherDailyDataModel): Boolean {
                return oldItem.city == newItem.city
            }

            override fun areContentsTheSame(oldItem: WeatherDailyDataModel, newItem: WeatherDailyDataModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

