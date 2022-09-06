package com.trendyol.showcase.showcase

sealed class ShowcaseState {
    object IDLE : ShowcaseState()
    object DESTROY : ShowcaseState()
}