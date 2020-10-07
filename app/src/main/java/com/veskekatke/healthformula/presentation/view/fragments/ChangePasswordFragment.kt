package com.veskekatke.healthformula.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_changepassword.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChangePasswordFragment : Fragment(R.layout.fragment_changepassword), KoinComponent {

    private val sharedPref : SharedPreferences by inject()

    private val userViewModel : MainContract.UserViewModel by sharedViewModel<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.settings)
        init()
    }

    private fun init(){
        initListeners()
    }

    private fun initListeners(){
        changePasswordButton.setOnClickListener {
            Toast.makeText(requireContext(), "cool story bro", Toast.LENGTH_SHORT).show()
        }
    }

}