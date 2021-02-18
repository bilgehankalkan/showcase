package com.trendyol.sample

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class Version2ShowcaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version2_showcase)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, SampleExpFragment(), "SAMPLE-FRAGMENT")
                .commit()
        }
    }


}

