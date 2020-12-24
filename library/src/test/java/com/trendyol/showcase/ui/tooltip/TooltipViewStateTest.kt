package com.trendyol.showcase.ui.tooltip

import android.view.View
import com.trendyol.showcase.R
import com.trendyol.showcase.`should be`
import com.trendyol.showcase.util.Constants.DEFAULT_ARROW_RESOURCE
import org.junit.Test

class TooltipViewStateTest {

    @Test
    fun `when imageUrl is empty then getImageViewVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(imageUrl = "")

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getImageViewVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when imageUrl is not empty then getImageViewVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(imageUrl = "https://cdn.dsmcdn.com/Assets/t/y/creative/mobile/InstantDelivery/instant-ty-onboarding.png")

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getImageViewVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when textPosition is CENTER then getTextPosition() returns 4`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(textPosition = TextPosition.CENTER)

        //then
        val expectedResult = 4
        val actualResult = tooltipViewState.getTextPosition()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when textPosition is END then getTextPosition() returns 3`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(textPosition = TextPosition.END)

        //then
        val expectedResult = 3
        val actualResult = tooltipViewState.getTextPosition()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when textPosition is not CENTER or END then getTextPosition() returns 2`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(textPosition = TextPosition.START)

        //then
        val expectedResult = 2
        val actualResult = tooltipViewState.getTextPosition()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when titleText is empty then getTitleVisibility() returns false`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(titleText = "")

        //then
        val expectedResult = false
        val actualResult = tooltipViewState.getTitleVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when titleText is not empty then getTitleVisibility() returns true`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(titleText = "Title")

        //then
        val expectedResult = true
        val actualResult = tooltipViewState.getTitleVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when descriptionText is empty then getDescriptionVisibility() returns false`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(descriptionText = "")

        //then
        val expectedResult = false
        val actualResult = tooltipViewState.getDescriptionVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when descriptionText is not empty then getDescriptionVisibility() returns true`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(descriptionText = "Description")

        //then
        val expectedResult = true
        val actualResult = tooltipViewState.getDescriptionVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowResource is DEFAULT_ARROW_RESOURCE then getTopArrowResource() returns ic_arrow_up`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowResource = DEFAULT_ARROW_RESOURCE)

        //then
        val expectedResult = R.drawable.ic_showcase_arrow_up
        val actualResult = tooltipViewState.getTopArrowResource()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowResource is not DEFAULT_ARROW_RESOURCE then getTopArrowResource() returns arrowResource`() {
        //when
        val givenArrowResource = android.R.drawable.arrow_down_float
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowResource = givenArrowResource)

        //then
        val actualResult = tooltipViewState.getTopArrowResource()

        actualResult `should be` givenArrowResource
    }

    @Test
    fun `when arrowPosition is UP then getTopArrowVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowPosition = ArrowPosition.UP)

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getTopArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowPosition is not UP then getBottomArrowVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowPosition = ArrowPosition.DOWN)

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getTopArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowResource is DEFAULT_ARROW_RESOURCE then getBottomArrowResource() returns ic_arrow_down`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowResource = DEFAULT_ARROW_RESOURCE)

        //then
        val expectedResult = R.drawable.ic_showcase_arrow_down
        val actualResult = tooltipViewState.getBottomArrowResource()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowResource is not DEFAULT_ARROW_RESOURCE then getBottomArrowResource() returns arrowResource`() {
        //when
        val givenArrowResource = android.R.drawable.arrow_down_float
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowResource = givenArrowResource)

        //then
        val actualResult = tooltipViewState.getBottomArrowResource()

        actualResult `should be` givenArrowResource
    }

    @Test
    fun `when arrowPosition is DOWN then getBottomArrowVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowPosition = ArrowPosition.DOWN)

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getBottomArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowPosition is not DOWN then getBottomArrowVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(arrowPosition = ArrowPosition.UP)

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getBottomArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when showCloseButton is true then getCloseButtonVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(showCloseButton = true)

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getCloseButtonVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when showCloseButton is false then getCloseButtonVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.getInstance().copy(showCloseButton = false)

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getCloseButtonVisibility()

        actualResult `should be` expectedResult
    }
}