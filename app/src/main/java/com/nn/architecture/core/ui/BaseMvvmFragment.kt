package com.nn.architecture.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.nn.architecture.R

abstract class BaseMvvmFragment<T : ViewModel, V : ViewDataBinding>(private val layoutId: Int) :
    Fragment(), BasicLiveEvent {

    protected lateinit var binding: V
    protected lateinit var viewModel: T

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        view?.let { view ->
            val parent = view.parent as ViewGroup?
            parent?.removeView(view)
        }

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setViews()
    }

    abstract fun initViewModel()

    abstract fun setViews()

    override fun showErrorMessage(error: Throwable?) {
        error?.let { Toast.makeText(context, it.message, Toast.LENGTH_LONG).show() }
            ?: run { Toast.makeText(context, R.string.error_general, Toast.LENGTH_LONG).show() }
    }

    override fun showErrorMessageLocalized(error: Throwable?) {
        context?.run {
            error?.message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            } ?: run{ Toast.makeText(this, R.string.error_general, Toast.LENGTH_LONG).show()}
        }
    }

    fun showToast(@StringRes res: Int) {
        Toast.makeText(context, resources.getString(res), Toast.LENGTH_SHORT).show()
    }
}