package com.nn.architecture.features.weathers

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.nn.architecture.R
import com.nn.architecture.core.api.Status
import com.nn.architecture.core.di.ViewModelFactory
import com.nn.architecture.core.ui.BaseMvvmFragment
import com.nn.architecture.databinding.FragmentWeatherCityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeathersFragment : BaseMvvmFragment<WeathersViewModel, FragmentWeatherCityBinding>(R.layout.fragment_weather_city)
        , WeathersViewModel.LiveEvents {

    val TAG = WeathersViewModel::class.simpleName

    @Inject
    lateinit var factory: ViewModelFactory<WeathersViewModel>

    private var recyclerViewAdapter: WeatherRecyclerAdapter? = null

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, factory).get(WeathersViewModel::class.java)
        viewModel?.uiEvent?.setEventReceiver(viewLifecycleOwner, this)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        observerChange()
    }

    override fun setViews() {
        context?.let {
            val divider = createItemDecoration(it)
            recyclerViewAdapter = WeatherRecyclerAdapter(it)

            binding?.recyclerView?.apply {
                adapter = recyclerViewAdapter
                addItemDecoration(divider)
            }

            binding?.queryString = viewModel?.queryString
            binding?.executePendingBindings()
        }
    }

    private fun observerChange() {
        viewModel?.listData?.observe(viewLifecycleOwner, Observer {
            if(it?.status == Status.SUCCESS) {
                recyclerViewAdapter?.submitList(it.data)
            }
        })

        viewModel?.queryString?.observe(viewLifecycleOwner, Observer {
            if(it.count() > 2) { viewModel?.queryWeathersCity() }
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