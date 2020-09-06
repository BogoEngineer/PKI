package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.veskekatke.healthformula.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.logInButton

class LogInFragment : Fragment(R.layout.fragment_login){
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        restore()
    }

    private fun restore(){
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        activity?.findViewById<DrawerLayout>(R.id.drawerLayout)?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun init(){
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        navController = Navigation.findNavController(requireView())

        activity?.findViewById<DrawerLayout>(R.id.drawerLayout)?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun setListeners(){
        logInButton.setOnClickListener{
            login()
        }

        forgotPasswordLink.setOnClickListener {
            forgotPassword()
        }
    }

    private fun login(){
        navController.navigate(R.id.action_logInFragment_to_homeFragment)
    }

    private fun forgotPassword(){
        Toast.makeText(activity?.applicationContext, "Unlucky", Toast.LENGTH_SHORT).show()
    }
}