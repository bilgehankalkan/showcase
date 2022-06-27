package com.trendyol.showcase.ui.showcase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.showcase.showcase.ShowcaseModel
import com.trendyol.showcase.util.ActionType

class ShowcaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, 0)

        val showcaseModel = intent?.extras?.getParcelable(BUNDLE_KEY) as? ShowcaseModel
        showcaseModel?.let { model ->
            val view = ShowcaseView(this).apply {
                setShowcaseModel(model)
                setClickListener { actionType, index ->
                    finishShowcase(actionType, index)
                }
            }
            setContentView(view)
        }
    }

    fun finishShowcase(actionType: ActionType, index: Int = -1) {
        val bundle = Bundle().apply {
            putSerializable(ShowcaseView.KEY_ACTION_TYPE, actionType)
            putInt(ShowcaseView.KEY_SELECTED_VIEW_INDEX, index)
        }
        setResult(Activity.RESULT_OK, Intent().apply { putExtras(bundle) })
        finish()
        overridePendingTransition(0, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        finishShowcase(ActionType.EXIT)
    }

    companion object {
        const val BUNDLE_KEY = "bundle_key"
    }
}
