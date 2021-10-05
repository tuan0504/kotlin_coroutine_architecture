package com.nn.architecture.core.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isShownOrGone")
fun setShownOrGone(view: View, isShown: Boolean) {
    view.visibility = if (isShown) View.VISIBLE else View.GONE
}

@BindingAdapter("isShownOrHidden")
fun setShownOrHidden(view: View, isHidden: Boolean) {
    view.visibility = if (isHidden) View.VISIBLE else View.INVISIBLE
}