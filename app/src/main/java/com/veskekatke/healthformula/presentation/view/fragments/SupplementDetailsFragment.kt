package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import kotlinx.android.synthetic.main.fragment_supplementdetails.*

class SupplementDetailsFragment(supplement : Supplement) : Fragment(R.layout.fragment_supplementdetails){

    var supplement = supplement

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        updateSupplement(supplement)
    }

    fun updateSupplement(supp : Supplement){
        supplement = supp
        Picasso
            .get()
            .load(supplement.picture)
            .into(supplementIv)

        contentSuppTv.text = supplement.description
        titleSuppTv.text = supplement.name
        manufacturerSuppTv.text = supplement.manufacturer
    }
}