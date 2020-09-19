package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.view.pagerAdapters.MealPlanPagerAdapter
import kotlinx.android.synthetic.main.fragment_mymealplan.*
import timber.log.Timber

class MyMealPlanFragment : Fragment(R.layout.fragment_mymealplan){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.myMealPlan)
        init()
    }

    private fun init(){
        initUI()
        initListeners()
    }

    private fun initUI(){
        viewPager2.adapter = MealPlanPagerAdapter(childFragmentManager, lifecycle)
        topTab.isInlineLabel = true
        TabLayoutMediator(topTab, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Current Plan"
                    tab.icon = resources.getDrawable(R.drawable.ic_list_alt_24px)
                }
                1 -> {
                    tab.text = "Allowed Foods"
                    tab.icon = resources.getDrawable(R.drawable.ic_done_24px)
                }
            }
            viewPager2.setCurrentItem(tab.position, true)
        }.attach()
    }

    private fun initListeners(){

    }
}