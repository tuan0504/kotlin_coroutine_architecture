package com.nn.architecture.features.weathers

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.nn.architecture.R
import com.nn.architecture.core.ui.BaseMvvmFragment
import com.nn.architecture.databinding.FragmentWeatherCityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeathersFragment : BaseMvvmFragment<WeathersViewModel, FragmentWeatherCityBinding>(R.layout.fragment_weather_city)
        , WeathersViewModel.LiveEvents {

    val TAG = WeathersViewModel::class.simpleName

    private lateinit var recyclerViewAdapter: WeatherRecyclerAdapter

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(WeathersViewModel::class.java)
        viewModel.uiEvent.setEventReceiver(viewLifecycleOwner, this)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        observerChange()
    }

    override fun setViews() {
        context?.let {

            binding.recyclerView.apply {
                recyclerViewAdapter = WeatherRecyclerAdapter(it)
                adapter = recyclerViewAdapter
                addItemDecoration(createItemDecoration(it))
            }

            binding.queryString = viewModel.queryString
            binding.executePendingBindings()
        }
    }

    private fun observerChange() {
        viewModel.listData.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.submitList(it)
        })

        viewModel.queryString.observe(viewLifecycleOwner, Observer {
            viewModel.queryWeathersCity()
        })
    }

    private fun createItemDecoration(context: Context) : DividerItemDecoration {
        return DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            val drawable = ContextCompat.getDrawable(context, R.drawable.search_grey_divider)
            if (drawable != null) {
                setDrawable(drawable)
            }
        }
    }
}