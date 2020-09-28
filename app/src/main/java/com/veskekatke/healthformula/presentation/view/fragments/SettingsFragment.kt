package com.veskekatke.healthformula.presentation.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class SettingsFragment : Fragment(R.layout.fragment_settings), KoinComponent{

    private val themeSp : SharedPreferences by inject()

    private val userViewModel : MainContract.UserViewModel by sharedViewModel<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.settings)
        init()
    }

    private fun init(){
        initStates()
        initObservers()
    }

    private fun initStates(){
        darkThemeSm.isChecked = themeSp.getString("theme", "")=="dark"
    }

    private fun initObservers(){
        userViewModel.get()
        darkThemeSm.setOnCheckedChangeListener { _, b ->
            with (themeSp.edit()) {
                putString("theme", if(b) "dark" else "light")
                commit()
            }
            darkThemeSm.isChecked = b
        }
    }
}