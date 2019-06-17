package com.trenyol.showcase.ui.showcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trenyol.showcase.ShowCaseApplication
import com.trenyol.showcase.showcase.ShowcaseModel

class ShowCaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (intent?.extras?.getSerializable(BUNDLE_KEY) as? ShowcaseModel)?.let { model ->
            val layout = ShowcaseView(this).apply {
                showcaseModel = model
            }
            setContentView(layout)
        }
    }

    override fun onBackPressed() {
        ShowCaseApplication.INSTANCE.cancelListener?.onCancel()
        super.onBackPressed()
    }

    companion object {

        const val BUNDLE_KEY = "BUNDLE_KEY"
    }
}
