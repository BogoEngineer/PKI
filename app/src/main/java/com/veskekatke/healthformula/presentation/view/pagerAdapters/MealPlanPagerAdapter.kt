package com.veskekatke.healthformula.presentation.view.pagerAdapters
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.veskekatke.healthformula.presentation.view.fragments.CurrentPlanFragment
import com.veskekatke.healthformula.presentation.view.fragments.FoodChoiceFragment

class MealPlanPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrentPlanFragment()
            1 -> FoodChoiceFragment()
            else -> CurrentPlanFragment()
        }
    }
}