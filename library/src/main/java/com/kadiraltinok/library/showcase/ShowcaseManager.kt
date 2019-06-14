package com.kadiraltinok.library.showcase

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.kadiraltinok.library.CancelListener
import com.kadiraltinok.library.ShowCaseApplication
import com.kadiraltinok.library.ui.showcase.HighlightType
import com.kadiraltinok.library.ui.showcase.ShowCaseActivity
import com.kadiraltinok.library.ui.tooltip.ArrowPosition
import com.kadiraltinok.library.util.Constants
import com.kadiraltinok.library.util.TooltipFieldUtil

class ShowcaseManager private constructor(private val showcaseModel: ShowcaseModel) {

    fun show(context: Context) {
        val intent = Intent(context, ShowCaseActivity::class.java)
        intent.putExtra(ShowCaseActivity.BUNDLE_KEY, showcaseModel)
        context.startActivity(intent)
    }

    class Builder {

        private lateinit var focusView: View
        private var titleText: String = ""
        private var descriptionText: String = ""
        @ColorInt
        private var titleTextColor: Int = Constants.DEFAULT_TEXT_COLOR
        @ColorInt
        private var descriptionTextColor: Int = Constants.DEFAULT_TEXT_COLOR
        @ColorInt
        private var popupBackgroundColor: Int = Constants.DEFAULT_POPUP_COLOR
        private var showCloseButton: Boolean = true
        @ColorInt
        private var closeButtonColor: Int = Constants.DEFAULT_TEXT_COLOR
        private var arrowPosition: ArrowPosition = ArrowPosition.AUTO
        private var highlightType: HighlightType = HighlightType.RECTANGLE
        private var cancelListener: CancelListener? = null
        private var arrowPercentage: Float? = null
        @ColorInt
        private var windowBackgroundColor: Int = Constants.DEFAULT_COLOR_BACKGROUND
        private var windowBackgroundAlpha: Int = Constants.DEFAULT_BACKGROUND_ALPHA
        private var titleTextSize: Float = Constants.DEFAULT_TITLE_TEXT_SIZE
        private var descriptionTextSize: Float = Constants.DEFAULT_DESCRIPTION_TEXT_SIZE

        fun view(view: View) = apply { focusView = view }
        fun titleText(title: String) = apply { titleText = title }
        fun descriptionText(description: String) = apply { descriptionText = description }
        fun titleTextColor(@ColorInt color: Int) = apply { titleTextColor = color }
        fun descriptionTextColor(@ColorInt color: Int) = apply { descriptionTextColor = color }
        fun backgroundColor(@ColorInt color: Int) = apply { popupBackgroundColor = color }
        fun closeButtonColor(@ColorInt color: Int) = apply { closeButtonColor = color }
        fun showCloseButton(show: Boolean) = apply { showCloseButton = show }
        fun arrowPosition(position: ArrowPosition) = apply { arrowPosition = position }
        fun highlightType(type: HighlightType) = apply { highlightType = type }
        fun cancelListener(listener: CancelListener) = apply { cancelListener = listener }
        fun arrowPercentage(@FloatRange(from = 0.1, to = 0.9) percentage: Float) = apply { arrowPercentage = percentage }
        fun windowBackgroundColor(@ColorInt color: Int) = apply { windowBackgroundColor = color }
        fun windowBackgroundAlpha(@IntRange(from = 0, to = 255) alpha: Int) = apply { windowBackgroundAlpha = alpha }
        /**
         *
         * titleTextSize in SP.
         */
        fun titleTextSize(size: Float) = apply { titleTextSize = size }
        /**
         *
         * descriptionTextSize in SP.
         */
        fun descriptionTextSize(size: Float) = apply { descriptionTextSize = size }

        fun build(): ShowcaseManager {
            if (!(::focusView.isInitialized)) {
                throw Exception("view should not be null!")
            }

            ShowCaseApplication.INSTANCE.cancelListener = cancelListener

            val rect = TooltipFieldUtil.getRect(focusView)

            val showcaseModel = ShowcaseModel(
                rect[0].toFloat(),
                rect[1].toFloat(),
                rect[2].toFloat(),
                rect[3].toFloat(),
                TooltipFieldUtil.calculateRadius(focusView),
                titleText,
                descriptionText,
                titleTextColor,
                descriptionTextColor,
                popupBackgroundColor,
                closeButtonColor,
                showCloseButton,
                arrowPosition,
                highlightType,
                arrowPercentage,
                windowBackgroundColor,
                windowBackgroundAlpha,
                titleTextSize,
                descriptionTextSize)

            return ShowcaseManager(showcaseModel)
        }
    }
}
