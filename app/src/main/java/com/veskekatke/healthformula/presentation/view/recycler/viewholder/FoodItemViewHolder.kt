package com.veskekatke.healthformula.presentation.view.recycler.viewholder

import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.foodItem.FoodItem
import com.veskekatke.healthformula.data.models.post.Post
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_post_list_item.view.*

class FoodItemViewHolder (
    override val containerView: View,
    onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener{
            onItemClicked.invoke(adapterPosition)
        }
    }

    fun bind(foodItem: FoodItem){
        containerView.findViewById<TextView>(R.id.foodItemTitleTv).text = foodItem.name
        containerView.findViewById<TextView>(R.id.foodItemContentTv).text =
            if(foodItem.description.length > 20) foodItem.description.substring(0, 20)+"..." else foodItem.description
    }
}