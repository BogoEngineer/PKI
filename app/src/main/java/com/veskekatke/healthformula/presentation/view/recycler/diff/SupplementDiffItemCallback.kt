package com.veskekatke.healthformula.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.veskekatke.healthformula.data.models.supplement.Supplement

class SupplementDiffItemCallback  : DiffUtil.ItemCallback<Supplement>(){
    override fun areItemsTheSame(oldItem: Supplement, newItem: Supplement): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Supplement, newItem: Supplement): Boolean {
        return true
    }

}