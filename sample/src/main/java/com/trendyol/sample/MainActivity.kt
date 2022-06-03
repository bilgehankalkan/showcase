package com.trendyol.sample

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.trendyol.showcase.showcase.ShowcaseManager
import com.trendyol.showcase.ui.showcase.HighlightType
import com.trendyol.showcase.ui.showcase.ShowcaseView
import com.trendyol.showcase.ui.slidablecontent.SlidableContent
import com.trendyol.showcase.ui.slidablecontent.slidableContent
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TextPosition
import com.trendyol.showcase.util.ActionType

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isStatusBarVisible) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        val buttonTop = findViewById<Button>(R.id.button_top)
        val buttonCenter = findViewById<Button>(R.id.button_center)
        val buttonBottom = findViewById<Button>(R.id.button_bottom)
        val buttonMultipleView = findViewById<Button>(R.id.button_focus_multiple_view)
        val buttonSlidableContent = findViewById<Button>(R.id.button_slidable_content)
        val textView = findViewById<View>(R.id.textView)
        val imageView = findViewById<View>(R.id.imageView)
        val imageTop = findViewById<View>(R.id.image_top)

        buttonTop.setOnClickListener {
            ShowcaseManager.Builder()
                .focus(buttonTop)
                .titleText("Title For Top!")
                .descriptionText("Simple, short description for top tooltip.")
                .titleTextColor(ContextCompat.getColor(baseContext, R.color.colorAccent))
                .titleTextFontFamily("sans-serif")
                .descriptionTextColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
                .backgroundColor(ContextCompat.getColor(baseContext, R.color.colorPrimaryDark))
                .imageUrl("https://cdn.dsmcdn.com/Assets/t/y/creative/mobile/InstantDelivery/instant-ty-onboarding.png")
                .showCloseButton(true)
                .cancellableFromOutsideTouch(true)
                .arrowPosition(ArrowPosition.AUTO)
                .highlightType(HighlightType.RECTANGLE)
                .textPosition(TextPosition.START)
                .windowBackgroundAlpha(255)
                .titleTextSize(30f)
                .statusBarVisible(isStatusBarVisible)
                .build()
                .show(this@MainActivity, REQUEST_CODE_SHOWCASE_CLICKED)
        }

        buttonCenter.setOnClickListener {
            ShowcaseManager.Builder()
                .focus(buttonCenter)
                .titleText("Title For Center!")
                .descriptionText("Center is here.")
                .titleTextColor(ContextCompat.getColor(baseContext, R.color.white))
                .titleTextStyle(Typeface.BOLD)
                .backgroundColor(ContextCompat.getColor(baseContext, R.color.colorPrimaryDark))
                .closeButtonColor(ContextCompat.getColor(baseContext, R.color.white))
                .showCloseButton(true)
                .arrowPosition(ArrowPosition.DOWN)
                .highlightType(HighlightType.CIRCLE)
                .textPosition(TextPosition.CENTER)
                .statusBarVisible(isStatusBarVisible)
                .build()
                .show(this@MainActivity, REQUEST_CODE_SHOWCASE_CLICKED)
        }

        buttonBottom.setOnClickListener {
            ShowcaseManager.Builder()
                .focus(buttonBottom)
                .titleText("Title without a description")
                .titleTextSize(16f)
                .titleTextFontFamily("sans-serif")
                .titleTextStyle(Typeface.BOLD)
                .showCloseButton(true)
                .arrowResource(android.R.drawable.arrow_down_float)
                .arrowPosition(ArrowPosition.AUTO)
                .highlightType(HighlightType.RECTANGLE)
                .highlightPadding(8f)
                .textPosition(TextPosition.START)
                .statusBarVisible(isStatusBarVisible)
                .build()
                .show(this@MainActivity, REQUEST_CODE_SHOWCASE_CLICKED)
        }

        buttonMultipleView.setOnClickListener {
            ShowcaseManager.Builder()
                .focus(textView, imageView)
                .titleText("Multiple View Focus")
                .titleTextSize(16f)
                .showCloseButton(true)
                .arrowPosition(ArrowPosition.UP)
                .highlightType(HighlightType.RECTANGLE)
                .highlightPadding(8f)
                .textPosition(TextPosition.START)
                .statusBarVisible(isStatusBarVisible)
                .build()
                .show(this@MainActivity, REQUEST_CODE_SHOWCASE_CLICKED)
        }

        buttonSlidableContent.setOnClickListener {
            ShowcaseManager.Builder()
                .focus(buttonSlidableContent)
                .setSlidableContentList(buildSlidableContentList())
                .showCloseButton(false)
                .cancellableFromOutsideTouch(true)
                .build()
                .show(this@MainActivity, REQUEST_CODE_SHOWCASE_CLICKED)
        }

        imageTop.setOnClickListener {
            ShowcaseManager.Builder()
                .focus(imageTop)
                .customContent(R.layout.view_custom_content)
                .cancellableFromOutsideTouch(true)
                .showcaseViewClickable(true)
                .statusBarVisible(isStatusBarVisible)
                .build()
                .show(this@MainActivity, REQUEST_CODE_SHOWCASE_CLICKED)
        }
    }

    private fun buildSlidableContentList(): List<SlidableContent> {
        val baseSlidableContent = slidableContent {
            imageUrl = "https://cdn.dsmcdn.com/Assets/t/y/creative/mobile/InstantDelivery/instant-ty-onboarding.png"
            titleTextColor = ContextCompat.getColor(baseContext, R.color.black)
            titleTextSize = 16f
            titleTextFontFamily = "sans-serif"
            titleTextStyle = Typeface.BOLD
            descriptionTextColor = ContextCompat.getColor(baseContext, R.color.colorPrimaryDark)
            descriptionTextSize = 14f
            descriptionTextFontFamily = "sans-serif"
            descriptionTextStyle = Typeface.NORMAL
            textPosition = TextPosition.CENTER
        }

        return with(baseSlidableContent) {
            listOf(
                copy(title = "Title 1", description = "Description 1"),
                copy(title = "Title 2", description = "Description 2"),
                copy(title = "Title 3", description = "Description 3"),
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode && REQUEST_CODE_SHOWCASE_CLICKED == requestCode && data != null) {
            val actionType = data.getSerializableExtra(ShowcaseView.KEY_ACTION_TYPE) as ActionType
            val selectedViewIndex = data.getIntExtra(ShowcaseView.KEY_SELECTED_VIEW_INDEX, -1)
            Log.i("MainActivity", "onActivityResult: actionType=${actionType.name} and selected view index=${selectedViewIndex}")
        }
    }

    companion object {
        private const val REQUEST_CODE_SHOWCASE_CLICKED = 101
        private const val isStatusBarVisible = true
    }
}