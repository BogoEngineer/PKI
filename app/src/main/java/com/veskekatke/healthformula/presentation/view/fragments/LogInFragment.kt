package com.veskekatke.healthformula.presentation.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.post.User
import com.veskekatke.healthformula.data.models.user.Credentials
import com.veskekatke.healthformula.presentation.view.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.logInButton
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class LogInFragment : Fragment(R.layout.fragment_login), KoinComponent{
    lateinit var navController : NavController

    private val sharedPref : SharedPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        if(sharedPref.getString("jwt", "")!= "") {
            navController.navigate(R.id.action_logInFragment_to_homeFragment)
            (requireActivity() as MainActivity).supportActionBar!!.show()
        }
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

        (requireActivity() as MainActivity).userViewModel.loggedIn.observe(viewLifecycleOwner, Observer {
            if(it.success){
                navController.navigate(R.id.action_logInFragment_to_homeFragment)
                (requireActivity() as MainActivity).supportActionBar!!.show()
            }
            else{
                if(it.msg != null && it.msg != "") Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        })
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
//        if(!checkInternetConnection()){
//            Toast.makeText(requireContext(), "Active internet connection is needed!", Toast.LENGTH_LONG).show()
//            return
//        }
//        (requireActivity() as MainActivity).userViewModel.authenticate(Credentials(usernameInput.text.toString(), passwordInput.text.toString()))

        var loginFlag = false

        var users = (activity as MainActivity).users

        for(user in users){
            if(user.username.equals(usernameInput.text.toString()) && user.password.equals(passwordInput.text.toString())){
                sharedPref.edit().putString("user", user.username)
                loginFlag = true
            }
        }

        if(!loginFlag) {
            Toast.makeText(context, "Wrong credentials!!", Toast.LENGTH_SHORT).show()
            return;
        }

        navController.navigate(R.id.action_logInFragment_to_homeFragment)
        (requireActivity() as MainActivity).supportActionBar!!.show()
    }

    private fun forgotPassword(){
        if(!checkInternetConnection()) {
            Toast.makeText(requireContext(), "Active internet connection is needed!", Toast.LENGTH_LONG).show()
            return
        }
        if(usernameInput.text.toString() == ""){
            Toast.makeText(activity?.applicationContext, "Fill in your email first", Toast.LENGTH_SHORT).show()
            return
        }
        (requireActivity() as MainActivity).userViewModel.resetPassword(usernameInput.text.toString())
        Toast.makeText(activity?.applicationContext, "Check your email for new password", Toast.LENGTH_SHORT).show()
    }

    private fun checkInternetConnection() : Boolean{
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}