package com.trendyol.showcase.ui.slidablecontent

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.showcase.R
import com.trendyol.showcase.databinding.ItemSlidableContentBinding

internal class SlidableContentAdapter(private val slidableContentList: List<SlidableContent>) :
    RecyclerView.Adapter<SlidableContentAdapter.ViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_slidable_content,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(slidableContentList[position])
    }

    override fun getItemCount(): Int = slidableContentList.size

    inner class ViewPagerViewHolder(private val binding: ItemSlidableContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SlidableContent) {
            with (binding) {
                viewState = SlidableContentViewState(item)

                textViewTitle.typeface = Typeface.create(
                    item.titleTextFontFamily,
                    item.titleTextStyle,
                )
                textViewDescription.typeface = Typeface.create(
                    item.descriptionTextFontFamily,
                    item.descriptionTextStyle,
                )
            }
        }
    }
}
