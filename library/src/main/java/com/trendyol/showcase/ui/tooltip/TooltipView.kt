package com.trendyol.showcase.ui.tooltip

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.trendyol.showcase.R
import com.trendyol.showcase.ui.slidablecontent.SlidableContent
import com.trendyol.showcase.databinding.LayoutTooltipBinding
import com.trendyol.showcase.ui.binding.BindingSetter.isVisible
import com.trendyol.showcase.ui.slidablecontent.SlidableContentAdapter
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
        with(binding) {
            this.tooltipViewState = tooltipViewState
            textViewTooltipTitle.typeface = Typeface.create(
                tooltipViewState.getTitleTextFontFamily(),
                tooltipViewState.getTitleTextStyle()
            )
            textViewTooltipDescription.typeface = Typeface.create(
                tooltipViewState.getDescriptionTextFontFamily(),
                tooltipViewState.getDescriptionTextStyle()
            )

            setupViewPager(tooltipViewState.showcaseModel.slidableContentList.orEmpty())

            executePendingBindings()
        }
    }

    private fun setupViewPager(slidableContentList: List<SlidableContent>) {
        with(binding) {
            viewPager.adapter = SlidableContentAdapter(slidableContentList)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    textViewSlidableContentPosition.text =
                        String.format(
                            resources.getString(R.string.slidable_content_position_text),
                            position + 1,
                            slidableContentList.size
                        )
                    textViewSlidableContentPosition.isVisible(
                        shouldShowSlidableContentPosition(slidableContentList.size)
                    )
                }
            })
        }
    }

    private fun shouldShowSlidableContentPosition(slidableContentListSize: Int): Boolean {
        return slidableContentListSize > 1
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
