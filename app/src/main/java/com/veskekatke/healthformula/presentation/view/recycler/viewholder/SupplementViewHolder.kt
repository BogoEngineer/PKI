package com.veskekatke.healthformula.presentation.view.recycler.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_supplement_list_item.view.*

class SupplementViewHolder(
    private val profile : Boolean,
    override val containerView: View,
    onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener{
            onItemClicked.invoke(adapterPosition)
        }

        /*if(profile){
            containerView.findViewById<CheckBox>(R.id.supplementCb).setOnCheckedChangeListener { compoundButton, b ->

            }
        }*/
    }

    fun bind(supplement : Supplement){
        containerView.findViewById<TextView>(R.id.supplementTitleTv).text = supplement.name
        if(!profile) containerView.findViewById<TextView>(R.id.supplementManufacturerTv).text = supplement.manufacturer
        /*containerView.findViewById<TextView>(R.id.supplementContentTv).text =
            if(supplement.description.length > 30) supplement.description.substring(0, 30)+"..." else supplement.description*/
    }
}
