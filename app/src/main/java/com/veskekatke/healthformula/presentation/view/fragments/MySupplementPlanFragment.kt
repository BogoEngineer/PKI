package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.navigation.NavigationView
import com.veskekatke.healthformula.R
import timber.log.Timber
import kotlin.concurrent.timer

class MySupplementPlanFragment : Fragment(R.layout.fragment_mysupplementplan){

    val index_name_map : HashMap<Int, String> = hashMapOf(
        R.id.before_breakfastFr to "Before breakfast",
        R.id.after_breakfastFr to "After breakfast",
        R.id.am_snackFr to "AM snack",
        R.id.before_lunchFr to "Before lunch",
        R.id.after_lunchFr to "After lunch",
        R.id.pm_snackFr to "PM snack",
        R.id.before_dinnerFr to "Before dinner",
        R.id.after_dinnerFr to "After dinner"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.mySupplementPlan)
        init()
    }

    private fun init(){
        initUI()
    }

    private fun initUI(){
        val fragmentMangerTransaction = requireActivity().supportFragmentManager.beginTransaction()
        for ((index, i) in index_name_map.keys.withIndex()) {
            fragmentMangerTransaction
                .add(i, MySupplementListFragment(index, index_name_map[i]!!))
        }
        fragmentMangerTransaction.commit()
    }
}
