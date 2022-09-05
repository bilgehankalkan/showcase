package com.trendyol.sample

import androidx.fragment.app.Fragment

class OneFragment : Fragment(R.layout.fragment_one){

    companion object{
        @JvmStatic
        fun newInstance() = OneFragment()
    }
}