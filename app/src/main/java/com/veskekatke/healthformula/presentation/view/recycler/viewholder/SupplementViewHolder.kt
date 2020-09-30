package com.veskekatke.healthformula.presentation.view.recycler.viewholder

import android.content.SharedPreferences
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_my_supplement_list_item.view.*
import kotlinx.android.synthetic.main.layout_supplement_list_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class SupplementViewHolder(
    private val profile : Boolean,
    override val containerView: View,
    onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer, KoinComponent {

    init {
        if(profile) containerView.supplementCb.setOnCheckedChangeListener { _, b ->
            onItemClicked.invoke(adapterPosition)
        }
    }

    fun bind(supplement : Supplement, checked : Boolean=false){
        containerView.findViewById<TextView>(R.id.supplementTitleTv).text = supplement.name
        if(!profile) containerView.findViewById<TextView>(R.id.supplementManufacturerTv).text = supplement.manufacturer
        if(profile) containerView.findViewById<CheckBox>(R.id.supplementCb).isChecked = checked
        /*containerView.findViewById<TextView>(R.id.supplementContentTv).text =
            if(supplement.description.length > 30) supplement.description.substring(0, 30)+"..." else supplement.description*/
    }
}
