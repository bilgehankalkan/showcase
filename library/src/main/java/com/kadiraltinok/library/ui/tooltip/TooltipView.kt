package com.kadiraltinok.library.ui.tooltip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kadiraltinok.library.R
import com.kadiraltinok.library.databinding.PopupTooltipBinding
import com.kadiraltinok.library.util.getActivity

class TooltipView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: PopupTooltipBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.popup_tooltip, rootView as ViewGroup, true)

    fun bind(tooltipViewState: TooltipViewState) {
        binding.imageViewTooltipClose.setOnClickListener {
            getActivity()?.onBackPressed()
        }
        binding.tooltipViewState = tooltipViewState
        binding.executePendingBindings()
    }
}
