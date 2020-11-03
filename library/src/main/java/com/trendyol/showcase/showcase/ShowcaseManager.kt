package com.trendyol.showcase.showcase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.trendyol.showcase.R
import com.trendyol.showcase.ui.showcase.HighlightType
import com.trendyol.showcase.ui.showcase.ShowcaseActivity
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TextPosition
import com.trendyol.showcase.util.Constants
import com.trendyol.showcase.util.TooltipFieldUtil
import com.trendyol.showcase.util.toRectF

data class ShowcaseManager private constructor(
    private val showcaseModel: ShowcaseModel,
    @StyleRes val resId: Int?
) {

    fun show(activity: Activity, requestCode: Int? = null) {
        if (showcaseModel.isDebugMode) return

        val intent = Intent(activity, ShowcaseActivity::class.java)
        val model = if (resId != null) readFromStyle(activity, resId) else showcaseModel
        intent.putExtra(ShowcaseActivity.BUNDLE_KEY, model)

        if (requestCode == null) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun show(fragment: Fragment, requestCode: Int? = null) {
        if (showcaseModel.isDebugMode) return

        fragment.activity?.let { activity ->
            val intent = Intent(activity, ShowcaseActivity::class.java)
            val model = if (resId != null) readFromStyle(activity, resId) else showcaseModel
            intent.putExtra(ShowcaseActivity.BUNDLE_KEY, model)

            if (requestCode == null) {
                fragment.startActivity(intent)
            } else {
                fragment.startActivityForResult(intent, requestCode)
            }
        }
    }

    private fun readFromStyle(context: Context, resId: Int): ShowcaseModel {
        val typedArray = context.obtainStyledAttributes(resId, R.styleable.Showcase_Theme)

        return showcaseModel.copy(
            titleTextColor = typedArray.getColor(R.styleable.Showcase_Theme_titleTextColor, showcaseModel.titleTextColor),
            descriptionTextColor = typedArray.getColor(R.styleable.Showcase_Theme_descriptionTextColor, showcaseModel.descriptionTextColor),
            closeButtonColor = typedArray.getColor(R.styleable.Showcase_Theme_closeButtonColor, showcaseModel.closeButtonColor),
            popupBackgroundColor = typedArray.getColor(R.styleable.Showcase_Theme_popupBackgroundColor, showcaseModel.popupBackgroundColor),
            windowBackgroundColor = typedArray.getColor(R.styleable.Showcase_Theme_windowBackgroundColor, showcaseModel.windowBackgroundColor),
            showCloseButton = typedArray.getBoolean(R.styleable.Showcase_Theme_showCloseButton, showcaseModel.showCloseButton),
            cancellableFromOutsideTouch = typedArray.getBoolean(R.styleable.Showcase_Theme_cancellableFromOutsideTouch, showcaseModel.cancellableFromOutsideTouch)
        ).also {
            typedArray.recycle()
        }
    }

    class Builder {

        private var focusViews: Array<out View>? = null
        private var titleText: String = Constants.DEFAULT_TEXT
        private var descriptionText: String = Constants.DEFAULT_TEXT

        @ColorInt
        private var titleTextColor: Int = Constants.DEFAULT_TEXT_COLOR

        @ColorInt
        private var descriptionTextColor: Int = Constants.DEFAULT_TEXT_COLOR

        @ColorInt
        private var popupBackgroundColor: Int = Constants.DEFAULT_POPUP_COLOR
        private var showCloseButton: Boolean = Constants.DEFAULT_CLOSE_BUTTON_VISIBILITY

        @ColorInt
        private var closeButtonColor: Int = Constants.DEFAULT_TEXT_COLOR
        private var highlightType: HighlightType = Constants.DEFAULT_HIGHLIGHT_TYPE

        @DrawableRes
        private var arrowResource: Int = Constants.DEFAULT_ARROW_RESOURCE
        private var arrowPercentage: Int? = null
        private var arrowPosition: ArrowPosition = Constants.DEFAULT_ARROW_POSITION

        @ColorInt
        private var windowBackgroundColor: Int = Constants.DEFAULT_COLOR_BACKGROUND
        private var windowBackgroundAlpha: Int = Constants.DEFAULT_BACKGROUND_ALPHA
        private var titleTextSize: Float = Constants.DEFAULT_TITLE_TEXT_SIZE
        private var descriptionTextSize: Float = Constants.DEFAULT_DESCRIPTION_TEXT_SIZE
        private var highlightPadding: Float = Constants.DEFAULT_HIGHLIGHT_PADDING_EXTRA

        @StyleRes
        private var resId: Int? = null
        private var cancellableFromOutsideTouch: Boolean = Constants.DEFAULT_CANCELLABLE_FROM_OUTSIDE_TOUCH
        private var isDebugMode: Boolean = false
        private var textPosition: TextPosition = Constants.DEFAULT_TEXT_POSITION
        private var imageUrl: String = Constants.DEFAULT_TEXT

        fun focus(view: View) = apply { focusViews = arrayOf(view) }

        fun focus(vararg view: View) = apply { focusViews = view }

        fun titleText(title: String) = apply { titleText = title }

        fun descriptionText(description: String) = apply { descriptionText = description }

        fun titleTextColor(@ColorInt color: Int) = apply { titleTextColor = color }

        fun descriptionTextColor(@ColorInt color: Int) = apply { descriptionTextColor = color }

        fun backgroundColor(@ColorInt color: Int) = apply { popupBackgroundColor = color }

        fun closeButtonColor(@ColorInt color: Int) = apply { closeButtonColor = color }

        fun showCloseButton(show: Boolean) = apply { showCloseButton = show }

        /**Custom icon resource for arrow.**/
        fun arrowResource(@DrawableRes resource: Int) = apply { arrowResource = resource }

        fun arrowPosition(position: ArrowPosition) = apply { arrowPosition = position }

        /**
         *
         * Custom positioning for arrow.
         */
        fun arrowPercentage(@IntRange(from = 0, to = 100) percentage: Int) = apply { arrowPercentage = percentage }

        fun highlightType(type: HighlightType) = apply { highlightType = type }

        /**
         *
         * Extra padding for highlight area.
         */
        fun highlightPadding(padding: Float) = apply { highlightPadding = padding }

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

        /**
         *
         * Resource id of an custom style named Showcase.Theme in project.
         */
        fun resId(@StyleRes res: Int) = apply { resId = res }

        fun cancellableFromOutsideTouch(cancellable: Boolean) = apply { cancellableFromOutsideTouch = cancellable }

        /**
         * Running in debug mode or not
         */
        fun isDebugMode(isDebug: Boolean) = apply { isDebugMode = isDebug }

        /**
         *
         * Custom positioning for text.
         */
        fun textPosition(position: TextPosition) = apply { textPosition = position }

        fun imageUrl(url: String) = apply { imageUrl = url }

        fun build(): ShowcaseManager {
            if (focusViews.isNullOrEmpty()) {
                throw Exception("view should not be null!")
            }

            val rect = Rect()

            focusViews!!.forEach {
                val viewRect = Rect()
                it.getGlobalVisibleRect(viewRect)
                rect.union(viewRect)
            }

            val showcaseModel = ShowcaseModel(
                rectF = rect.toRectF(),
                radius = TooltipFieldUtil.calculateRadius(rect),
                titleText = titleText,
                descriptionText = descriptionText,
                titleTextColor = titleTextColor,
                descriptionTextColor = descriptionTextColor,
                popupBackgroundColor = popupBackgroundColor,
                closeButtonColor = closeButtonColor,
                showCloseButton = showCloseButton,
                highlightType = highlightType,
                arrowResource = arrowResource,
                arrowPosition = arrowPosition,
                arrowPercentage = arrowPercentage,
                windowBackgroundColor = windowBackgroundColor,
                windowBackgroundAlpha = windowBackgroundAlpha,
                titleTextSize = titleTextSize,
                descriptionTextSize = descriptionTextSize,
                highlightPadding = highlightPadding,
                cancellableFromOutsideTouch = cancellableFromOutsideTouch,
                isDebugMode = isDebugMode,
                textPosition = textPosition,
                imageUrl = imageUrl
            )

            return ShowcaseManager(showcaseModel = showcaseModel, resId = resId)
        }
    }

    companion object {
        const val HIGHLIGHT_CLICKED = "highlight_clicked"
    }
}
