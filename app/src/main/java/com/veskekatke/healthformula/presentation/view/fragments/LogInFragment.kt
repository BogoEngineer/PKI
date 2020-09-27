package com.veskekatke.healthformula.presentation.view.fragments

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.view.activities.MainActivity
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
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        val sfL = object: ShapeDrawable.ShaderFactory(){
            @RequiresApi(Build.VERSION_CODES.M)
            override fun resize(p0: Int, p1: Int): Shader {
                val lg = LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), topView.height.toFloat(),
                    intArrayOf(
                        requireActivity().getColor(R.color.navViewFade1Dark),
                        requireActivity().getColor(R.color.navViewFade2Dark),
                        requireActivity().getColor(R.color.navViewFade2Dark),
                        requireActivity().getColor(R.color.navViewFade3Dark),
                        requireActivity().getColor(R.color.navViewFade3Dark)),
                    floatArrayOf(0.20f, 0.40f, 0.50f, 0.75f, 1f),
                    Shader.TileMode.REPEAT)
                return lg
            }

        }
        val pL = PaintDrawable()
        pL.shape = RectShape()
        pL.shaderFactory = sfL
        topView.background = pL as Drawable


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
        (requireActivity() as MainActivity).supportActionBar!!.show()
    }

    private fun forgotPassword(){
        Toast.makeText(activity?.applicationContext, "Unlucky", Toast.LENGTH_SHORT).show()
    }
}