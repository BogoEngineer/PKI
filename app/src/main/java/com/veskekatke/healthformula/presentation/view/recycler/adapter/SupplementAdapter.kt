package com.veskekatke.healthformula.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.presentation.view.recycler.diff.SupplementDiffItemCallback
import com.veskekatke.healthformula.presentation.view.recycler.viewholder.SupplementViewHolder

class SupplementAdapter(
    private val profile: Boolean,
    supplementDiffItemCallback: SupplementDiffItemCallback,
    private val onPostClicked: (Supplement) -> Unit) : ListAdapter<Supplement, SupplementViewHolder>(supplementDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(if(!profile) R.layout.layout_supplement_list_item else R.layout.layout_my_supplement_list_item, parent, false)
        return SupplementViewHolder(profile, containerView) {
            val supplement = getItem(it)
            onPostClicked.invoke(supplement)
        }
    }

    override fun onBindViewHolder(holder: SupplementViewHolder, position: Int) {
        val supplement = getItem(position)
        holder.bind(supplement)
    }

}