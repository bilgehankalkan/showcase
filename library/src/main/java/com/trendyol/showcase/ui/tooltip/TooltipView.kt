package com.trendyol.showcase.ui.tooltip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.trendyol.showcase.R
import com.trendyol.showcase.databinding.LayoutTooltipBinding
import com.trendyol.showcase.util.ActionType

class TooltipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: LayoutTooltipBinding

    private var clickListener: ((ActionType, Int) -> (Unit))? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_tooltip, rootView as ViewGroup, true)

        with(binding) {
            layoutContents.setOnClickListener {
                sendActionType(ActionType.SHOWCASE_CLICKED)
            }

            imageViewTooltipClose.setOnClickListener {
                sendActionType(ActionType.EXIT)
            }
        }
    }

    internal fun bind(tooltipViewState: TooltipViewState) {
        binding.tooltipViewState = tooltipViewState
        binding.executePendingBindings()
    }

    fun setCustomContent(@LayoutRes customContent: Int) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(customContent, null)
        binding.layoutContents.addView(view)
    }

    fun setClickListener(listener: (ActionType, Int) -> (Unit)) {
        clickListener = listener
    }

    private fun sendActionType(actionType: ActionType, index: Int = -1) {
        clickListener?.invoke(actionType, index)
    }
}
