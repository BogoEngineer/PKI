package com.veskekatke.healthformula.presentation.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.post.User
import com.veskekatke.healthformula.data.models.user.PasswordInformation
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.activities.MainActivity
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_changepassword.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChangePasswordFragment : Fragment(R.layout.fragment_changepassword), KoinComponent {

    private val sharedPref : SharedPreferences by inject()

    lateinit var navController : NavController

    var loggedInUser : User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.settings)
        init()
    }

    private fun init(){
        navController = Navigation.findNavController(requireView())
        initListeners()
        initObservers()

        var users = (activity as MainActivity).users



        for(user in users){
            if(user.username == sharedPref.getString("user", "korisnik")){
                loggedInUser = user
            }
        }

Toast.makeText(context, loggedInUser.toString(), Toast.LENGTH_LONG)
        usernameT.setText(loggedInUser?.username)
        firstNameT.setText(loggedInUser?.firstName)
        lastNameT.setText(loggedInUser?.lastName)
        phoneT.setText(loggedInUser?.phone)
        addressET.setText(loggedInUser?.address)
        oldPassword.setText(loggedInUser?.password)
    }

    private fun initObservers(){
//        (requireActivity() as MainActivity).userViewModel.changedPassword.observe(viewLifecycleOwner, Observer {
//            if(it != null){
//                if(it.success){
//                    (requireActivity() as MainActivity).userViewModel.logOut()
//                    sharedPref.edit().clear().commit()
//                    navController.popBackStack(R.id.homeFragment, true)
//                    navController.navigate(R.id.logInFragment)
//                    (requireActivity() as MainActivity).userViewModel.enableChangePassword()
//                }
//                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    private fun initListeners(){
        changePasswordButton.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword(){
//        if(!checkInternetConnection()){
//            Toast.makeText(requireContext(), "Active internet connection is needed!", Toast.LENGTH_LONG).show()
//            return
//        }
//        if(newPassword1.text.toString() != newPassword2.text.toString()){
//            Toast.makeText(context, "Passwords aren't matching for new password", Toast.LENGTH_SHORT).show()
//            return
//        }
//        (requireActivity() as MainActivity).userViewModel.changePassword(PasswordInformation(
//            (requireActivity() as MainActivity).userViewMoodel.user.value!!.email,
//            oldPassword.text.toString(),
//            newPassword1.text.toString()
//        ))

        var newUser = User(usernameT.text.toString(), oldPassword.text.toString(), firstNameT.text.toString(), lastNameT.text.toString(), phoneT.text.toString(), addressET.text.toString())

        (activity as MainActivity).users.remove(loggedInUser)
        (activity as MainActivity).users.add(newUser)
        Toast.makeText(context, "Info changed successfully", Toast.LENGTH_LONG).show()
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