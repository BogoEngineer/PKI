package com.veskekatke.healthformula.presentation.view.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.fragments.HomeFragment
import com.veskekatke.healthformula.presentation.view.fragments.SettingsFragment
import com.veskekatke.healthformula.presentation.viewmodel.SupplementViewModel
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import okhttp3.internal.checkOffsetAndCount
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.w3c.dom.Text
import timber.log.Timber
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(R.layout.activity_main), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, KoinComponent {
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var navController : NavController
    var wantExit = false

    private lateinit var starterIntent : Intent


    private val sharedPref : SharedPreferences by inject()

    @RequiresApi(Build.VERSION_CODES.M)
    private val onSharedPreferenceChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { p0, p1 ->
            val theme = sharedPref.getString("theme", "")
            fragmentField.background= if(theme == "dark")
                getDrawable(R.color.app_background_dark) else getDrawable(R.color.app_background_light)
            finish()
            startActivity(starterIntent.putExtra("fromSettings",true))
        }


    private val userViewModel : MainContract.UserViewModel by viewModel<UserViewModel>()

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(if(sharedPref.getString("theme", "")=="dark") R.style.AppThemeDark else R.style.AppThemeLight);
        super.onCreate(savedInstanceState)
        starterIntent = intent
        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init(){
        initUI()
        initNavBar()
        setListeners()
        initObservers()
        if(intent.getBooleanExtra("fromSettings", false)) {
            navController.navigate(R.id.homeFragment)
            navController.navigate(R.id.settingsFragment)
        }
    }

    /*@RequiresApi(Build.VERSION_CODES.M)
    private fun deepChangeTextColor(parentLayout: ViewGroup, colorId: Int){
        for(count in 0..parentLayout.childCount){
            val view = parentLayout.getChildAt(count)
            if(view is TextView){
                (view as TextView).setTextColor(getColor(colorId))
            }
            else if(view is ViewGroup){
                deepChangeTextColor(view as ViewGroup, colorId)
            }
        }
    }*/

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initUI(){
        val theme = sharedPref.getString("theme","")
        if(theme =="") {
            with((sharedPref.edit())){
                putString("theme", "light")
                commit()
            }
        }
        fragmentField.background= if(theme == "dark")
            getDrawable(R.color.app_background_dark) else getDrawable(R.color.app_background_light)
        this.setTheme(if(theme=="dark") R.style.AppThemeDark else R.style.AppThemeLight)
    }

    private fun initObservers(){
        userViewModel.user.observe(this, Observer {
            if(userViewModel.user.value?.profile_picture != null){
                Picasso
                    .get()
                    .load(userViewModel.user.value?.profile_picture)
                    .error(R.drawable.undefined_profile_pic)
                    .into(profile_image)
            }else {
                Picasso
                    .get()
                    .load(R.drawable.undefined_profile_pic)
                    .into(profile_image)
            }
        })
        userViewModel.fetch()
        //userViewModel.get()


        sharedPref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangedListener)
    }

    private fun setListeners(){
        val navigationView: NavigationView = findViewById(R.id.navView)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initNavBar(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentField) as NavHostFragment
        navController = navHostFragment.navController

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setCheckedItem(R.id.homeFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        wantExit = false
        when(item.itemId){
            R.id.home -> {
                navController.popBackStack(R.id.homeFragment, true)
                navController.navigate(R.id.homeFragment)
            }
            R.id.allSupplements -> {
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.allSupplementsFragment)
            }
            R.id.myMealPlan -> {
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.myMealPlanFragment)
            }
            R.id.mySupplementPlan -> {
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.mySupplementPlanFragment)
            }
            R.id.about -> {
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.aboutFragment)
            }
            R.id.logout -> {
                navController.popBackStack(R.id.logInFragment, false)
                navController.navigate(R.id.logInFragment)
            }
            R.id.settings -> {
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.settingsFragment)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentField)!!.childFragmentManager.fragments.get(0)
            if(currentFragment is HomeFragment){
                if(wantExit) {
                    android.os.Process.killProcess(android.os.Process.myPid())
                    exitProcess(0)
                }
                Toast.makeText(this.applicationContext, R.string.doupletap, Toast.LENGTH_LONG).show()
                wantExit = true
            }else{
                super.onBackPressed()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.profile_image -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                        //permission denied
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                        //show popup to request runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else{
                    //system OS is < Marshmallow
                    pickImageFromGallery();
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            profile_image.setImageURI(data?.data)
        }
    }
}