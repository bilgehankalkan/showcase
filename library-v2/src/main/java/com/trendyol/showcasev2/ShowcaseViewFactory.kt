package com.trendyol.showcasev2

import android.content.Context
import android.graphics.Rect
import android.view.View

interface ShowcaseViewFactory {

    fun buildShowcaseView(
        viewContext: Context,
        configuration: ShowcaseView.Configuration,
        viewClipper: TargetViewClipper,
        inflateTooltip: ((ShowcaseView, Rect) -> View)?
    ): ShowcaseView
}

class DefaultShowcaseViewFactory constructor() : ShowcaseViewFactory {

    override fun buildShowcaseView(
        viewContext: Context,
        configuration: ShowcaseView.Configuration,
        viewClipper: TargetViewClipper,
        inflateTooltip: ((ShowcaseView, Rect) -> View)?
    ): ShowcaseView {
        return ShowcaseView(viewContext).apply {
            setConfiguration(configuration)
            setViewClipper(viewClipper)
            this.inflateTooltip = inflateTooltip
        }
    }
}
