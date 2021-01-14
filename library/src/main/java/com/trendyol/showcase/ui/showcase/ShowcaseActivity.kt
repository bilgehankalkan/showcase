package com.trendyol.showcase.ui.showcase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.showcase.showcase.ShowcaseModel

class ShowcaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, 0)

        val showcaseModel = intent?.extras?.getParcelable(BUNDLE_KEY) as? ShowcaseModel
        showcaseModel?.let { model ->
            val layout = ShowcaseView(this).apply {
                this.showcaseModel = model
            }
            setContentView(layout)
        }
    }

    fun onBackPress(isHighlightClicked: Boolean = false) {
        setResult(Activity.RESULT_OK, Intent().putExtra(HIGHLIGHT_CLICKED, isHighlightClicked))
        finish()
        overridePendingTransition(0, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        onBackPress()
    }

    companion object {

        const val BUNDLE_KEY = "bundle_key"
        const val HIGHLIGHT_CLICKED = "highlight_clicked"
    }
}
