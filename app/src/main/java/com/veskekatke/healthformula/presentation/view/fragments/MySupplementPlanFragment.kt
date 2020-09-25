package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.data.models.supplement.SupplementResponse
import com.veskekatke.healthformula.data.models.user.UserResponse
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.nav_header.*
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.concurrent.timer

class MySupplementPlanFragment : Fragment(R.layout.fragment_mysupplementplan){

    private val userViewModel : UserViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.mySupplementPlan)
        init()
    }

    private fun init(){
        initObservers()
        initUI()
    }

    private fun initObservers(){
        userViewModel.user.observe(viewLifecycleOwner, Observer {
        })
        userViewModel.get()
    }


    private fun initUI(){
        val fragmentMangerTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentMangerTransaction.add(R.id.before_breakfastFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.before_breakfast.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "Before breakfast"))
        /*fragmentMangerTransaction.add(R.id.after_breakfastFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.after_breakfast.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "After breakfast"))
        fragmentMangerTransaction.add(R.id.am_snackFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.am_snack.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "AM snack"))
        fragmentMangerTransaction.add(R.id.before_lunchFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.before_lunch.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "Before lunch"))
        fragmentMangerTransaction.add(R.id.after_lunchFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.after_lunch.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "After lunch"))
        fragmentMangerTransaction.add(R.id.pm_snackFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.pm_snack.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "PM snack"))
        fragmentMangerTransaction.add(R.id.before_dinnerFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.before_dinner.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "Before dinner"))
        fragmentMangerTransaction.add(R.id.after_dinnerFr, MySupplementListFragment(userViewModel.user.value!!.phase.supplement_plan.after_dinner.map {
            Supplement(
                it._id,
                it.name,
                it.manufacturer,
                it.description,
                it.image
            )
        }, "After dinner"))*/
        fragmentMangerTransaction.commit()
    }
}
