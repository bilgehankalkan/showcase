package com.trendyol.sample

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.trendyol.sample.SampleFragment.Companion.updateStatusBar

class SecondarySampleFragment(private val index: Int) : Fragment(R.layout.fragment_secondary_sample) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.text_id).text = "Fragment $index"
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden.not()) {
            updateStatusBar(true)
        }
    }
}
