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
import com.trendyol.showcasev2.CircleTargetViewClipper

@Deprecated("use SampleExpFragment")
class SampleFragment : Fragment() {

    private lateinit var extendedLifecycleOwner: ExtendedLifecycleOwner
    private var binding: FragmentVersion2ShowcaseBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_version2_showcase, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extendedLifecycleOwner = ExtendedLifecycleOwner(viewLifecycleOwner)
        binding!!.apply {
            buttonRectShowcase.setOnClickListener {
                targetRect.startShowcaseView(viewLifecycleOwner)
            }

            buttonRoundedShowcase.setOnClickListener {
                targetRound.startShowcaseView(viewLifecycleOwner, viewClipper = CircleTargetViewClipper())
                targetRound.postDelayed({ targetRound.dismissShowcase() }, 5000)
            }

            buttonMultipleShowcase.setOnClickListener {
                targetMultiple.startShowcaseView(viewLifecycleOwner, viewClipper = CircleTargetViewClipper())
            }

            buttonSizeChange.setOnClickListener {
                targetSizeChange.startShowcaseView(viewLifecycleOwner)
                targetSizeChange.postDelayed(
                    {
                        imageSize.layoutParams = imageSize.layoutParams.apply {
                            width *= 2
                            height *= 2
                        }
                    }, 2000)
            }

            buttonScroll.setOnClickListener {
                targetScroll.startShowcaseView(viewLifecycleOwner)
                targetScroll.postDelayed(
                    {
                        scrollView.smoothScrollBy(0, 500)
                    }, 2000)
            }

            buttonLifecycle.setOnClickListener {
                targetLifecycle.startShowcaseView(extendedLifecycleOwner)
                targetLifecycle.postDelayed(
                    {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .hide(this@SampleFragment)
                            .add(R.id.container, Sample2Fragment(), "SAMPLE-2-FRAGMENT")
                            .addToBackStack(null)
                            .commit()
                    }, 3000)
            }

            buttonPosition.setOnClickListener {
                targetPosition.startShowcaseView(viewLifecycleOwner)
                targetPosition.postDelayed(
                    {
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(rootConstraintLayout)
                        constraintSet.clear(R.id.targetPosition, ConstraintSet.END)
                        constraintSet.applyTo(rootConstraintLayout)
                    }, 2000)
            }
            targetRect.dismissListener = {
                true
            }
            targetRound.dismissListener = {
                false
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