package com.trendyol.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.trendyol.sample.databinding.FragmentVersion2ShowcaseBinding
import com.trendyol.sample.databinding.FragmentWithoutCustomViewExpBinding
import com.trendyol.showcasev2.CircleTargetViewClipper
import com.trendyol.showcasev2.DefaultShowcaseViewFactory
import com.trendyol.showcasev2.RectangleTargetViewClipper
import com.trendyol.showcasev2.ShowcaseManager

class SampleExpFragment : Fragment() {

    private lateinit var extendedLifecycleOwner: ExtendedLifecycleOwner
    private var binding: FragmentWithoutCustomViewExpBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_without_custom_view_exp, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extendedLifecycleOwner = ExtendedLifecycleOwner(viewLifecycleOwner)
        val manager = ShowcaseManager(extendedLifecycleOwner, DefaultShowcaseViewFactory())
        binding!!.apply {
            buttonRectShowcase.setOnClickListener {
                manager.focus(targetRect, RectangleTargetViewClipper()) { true }
            }

            buttonRoundedShowcase.setOnClickListener {
                manager.focus(targetRound, CircleTargetViewClipper()) { false }
                targetRound.postDelayed({ manager.dismiss() }, 5000)
            }

            buttonMultipleShowcase.setOnClickListener {
                manager.focus(targetMultiple, CircleTargetViewClipper())
            }

            buttonSizeChange.setOnClickListener {
                manager.focus(targetSizeChange)
                targetSizeChange.postDelayed(
                    {
                        targetSizeChange.layoutParams = targetSizeChange.layoutParams.apply {
                            width *= 2
                            height *= 2
                        }
                    }, 2000)
            }

            buttonScroll.setOnClickListener {
                manager.focus(targetScroll)
                targetScroll.postDelayed(
                    {
                        scrollView.smoothScrollBy(0, 500)
                    }, 2000)
            }

            buttonLifecycle.setOnClickListener {
                manager.focus(targetLifecycle)
                targetLifecycle.postDelayed(
                    {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .hide(this@SampleExpFragment)
                            .add(R.id.container, Sample2Fragment(), "SAMPLE-2-FRAGMENT")
                            .addToBackStack(null)
                            .commit()
                    }, 3000)
            }

            buttonPosition.setOnClickListener {
                manager.focus(targetPosition)
                targetPosition.postDelayed(
                    {
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(rootConstraintLayout)
                        constraintSet.clear(R.id.targetPosition, ConstraintSet.END)
                        constraintSet.applyTo(rootConstraintLayout)
                    }, 2000)
            }
        }

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (view == null) return
        if (hidden.not()) {
            extendedLifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        } else {
            extendedLifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}