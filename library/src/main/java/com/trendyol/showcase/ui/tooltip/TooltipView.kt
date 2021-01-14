package com.trendyol.showcase.ui.tooltip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.trendyol.showcase.R
import com.trendyol.showcase.databinding.LayoutTooltipBinding
import com.trendyol.showcase.util.getShowcaseActivity

class TooltipView : ConstraintLayout {

    private val binding: LayoutTooltipBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_tooltip,
        rootView as ViewGroup,
        true
    )

    init {
        binding.imageViewTooltipClose.setOnClickListener {
            getShowcaseActivity()?.onBackPress()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    internal fun bind(tooltipViewState: TooltipViewState) {
        binding.tooltipViewState = tooltipViewState
        binding.executePendingBindings()
    }

    fun setCustomContent(@LayoutRes customContent: Int) {
        binding.layoutContents.addView(LayoutInflater.from(context).inflate(customContent, null))
    }
}
