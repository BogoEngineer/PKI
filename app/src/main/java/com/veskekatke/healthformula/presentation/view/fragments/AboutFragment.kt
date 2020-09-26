package com.veskekatke.healthformula.presentation.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.veskekatke.healthformula.R
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class AboutFragment : Fragment(R.layout.fragment_about){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.about)
    }


}