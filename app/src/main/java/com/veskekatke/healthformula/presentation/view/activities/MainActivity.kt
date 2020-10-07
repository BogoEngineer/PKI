package com.veskekatke.healthformula.presentation.view.activities

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.alarm.NotificationReceiver
import com.veskekatke.healthformula.presentation.view.fragments.HomeFragment
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(R.layout.activity_main), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, KoinComponent {
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var navController : NavController
    var wantExit = false

    private val sharedPref : SharedPreferences by inject()

    @RequiresApi(Build.VERSION_CODES.M)
    private val onSharedPreferenceChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { p0, p1 ->
            if(p1=="theme"){
                val theme = sharedPref.getString("theme", "")
                setFadingBackground(theme=="dark")
                setFadingNavbar(theme=="dark")
                recreate()
            }
        }


    val userViewModel : MainContract.UserViewModel by viewModel<UserViewModel>()

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
        init()
    }

    private fun myAlarm(){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.set(Calendar.MINUTE, 22)

        if(calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)

        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = (getSystemService(Context.ALARM_SERVICE) as AlarmManager)

        if(alarmManager != null) alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.channel_id), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init(){
        //createNotificationChannel()
        initUI()
        initNavBar()
        setListeners()
        initObservers()
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

    private fun setFadingBackground(dark: Boolean){
        if(dark){
            val sfB = object: ShapeDrawable.ShaderFactory(){
                @RequiresApi(Build.VERSION_CODES.M)
                override fun resize(p0: Int, p1: Int): Shader {
                    val lg = LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), fragmentField.height.toFloat(),
                        intArrayOf(
                            getColor(R.color.navViewFade1Dark),
                            getColor(R.color.navViewFade2Dark),
                            getColor(R.color.navViewFade2Dark),
                            getColor(R.color.navViewFade3Dark),
                            getColor(R.color.navViewFade3Dark)),
                        floatArrayOf(0.20f, 0.40f, 0.50f, 0.75f, 1f),
                        Shader.TileMode.REPEAT)
                    return lg
                }

            }
            val pB = PaintDrawable()
            pB.shape = RectShape()
            pB.shaderFactory = sfB
            fragmentField.background = pB as Drawable
        }else{
            val sfB = object: ShapeDrawable.ShaderFactory(){
                @RequiresApi(Build.VERSION_CODES.M)
                override fun resize(p0: Int, p1: Int): Shader {
                    val lg = LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), fragmentField.height.toFloat(),
                        intArrayOf(
                            getColor(R.color.navViewFade1Light),
                            getColor(R.color.navViewFade2Light),
                            getColor(R.color.navViewFade3Light),
                            getColor(R.color.navViewFade4Light),
                            getColor(R.color.navViewFade5Light)),
                        floatArrayOf(0.1f, 0.30f, 0.50f, 0.75f, 1f),
                        Shader.TileMode.REPEAT)
                    return lg
                }

            }
            val pB = PaintDrawable()
            pB.shape = RectShape()
            pB.shaderFactory = sfB
            fragmentField.background = pB as Drawable
        }
    }

    private fun setFadingNavbar(dark: Boolean){
        if(dark){
            val sfN = object: ShapeDrawable.ShaderFactory(){
                @RequiresApi(Build.VERSION_CODES.M)
                override fun resize(p0: Int, p1: Int): Shader {
                    val lg = LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), navView.height.toFloat(),
                        intArrayOf(
                            getColor(R.color.navViewFade1Dark),
                            getColor(R.color.navViewFade2Dark),
                            getColor(R.color.navViewFade2Dark),
                            getColor(R.color.navViewFade3Dark),
                            getColor(R.color.navViewFade3Dark)),
                        floatArrayOf(0.20f, 0.40f, 0.50f, 0.75f, 1f),
                        Shader.TileMode.REPEAT)
                    return lg
                }

            }
            val pN = PaintDrawable()
            pN.shape = RectShape()
            pN.shaderFactory = sfN
            navView.background = pN as Drawable
        }else{
            val sfN = object: ShapeDrawable.ShaderFactory(){
                @RequiresApi(Build.VERSION_CODES.M)
                override fun resize(p0: Int, p1: Int): Shader {
                    val lg = LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), navView.height.toFloat(),
                        intArrayOf(
                            getColor(R.color.navViewFade1Light),
                            getColor(R.color.navViewFade2Light),
                            getColor(R.color.navViewFade3Light),
                            getColor(R.color.navViewFade4Light),
                            getColor(R.color.navViewFade5Light)),
                        floatArrayOf(0.1f, 0.30f, 0.50f, 0.75f, 1f),
                        Shader.TileMode.REPEAT)
                    return lg
                }

            }
            val pN = PaintDrawable()
            pN.shape = RectShape()
            pN.shaderFactory = sfN
            navView.background = pN as Drawable
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initUI(){
        val theme = sharedPref.getString("theme","")
        if(theme =="") {
            with((sharedPref.edit())){
                putString("theme", "light")
                commit()
            }
        }
        //fragmentField.background= if(theme == "dark") getDrawable(R.color.app_background_dark) else getDrawable(R.color.app_background_light)

        //navView.background = if(theme == "dark") getDrawable(R.color.app_background_dark) else getDrawable(R.color.app_background_light)
        setFadingNavbar(theme=="dark")
        setFadingBackground(theme=="dark")


        // Init notification UI
        /*var builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_pill)
            .setContentTitle(getString(R.string.reminder))
            .setContentText(getString(R.string.notification_text))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.notification_text)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }, 0))

        //show notification
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }*/
        if(sharedPref.getBoolean("notifications", true)) myAlarm()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initObservers(){
        userViewModel.user.observe(this, Observer {
            if(userViewModel.user.value?.profile_picture != null && profile_image != null){
                Picasso
                    .get()
                    .load(userViewModel.user.value?.profile_picture)
                    .error(R.drawable.undefined_profile_pic)
                    .into(profile_image)
            }else if(profile_image != null && userViewModel.user.value?.profile_picture==null) {
                Picasso
                    .get()
                    .load(R.drawable.undefined_profile_pic)
                    .into(profile_image)
            }
        })
        //userViewModel.fetch(sharedPref.getString("userId", "")!!)


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
                supportActionBar!!.show()
                navController.popBackStack(R.id.homeFragment, true)
                navController.navigate(R.id.homeFragment)
            }
            R.id.allSupplements -> {
                supportActionBar!!.show()
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.allSupplementsFragment)
            }
            R.id.myMealPlan -> {
                supportActionBar!!.show()
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.myMealPlanFragment)
            }
            R.id.mySupplementPlan -> {
                supportActionBar!!.show()
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.mySupplementPlanFragment)
            }
            R.id.about -> {
                supportActionBar!!.hide()
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.aboutFragment)
            }
            R.id.logout -> {
                supportActionBar!!.hide()
                userViewModel.logOut()
                sharedPref.edit().clear().commit()
                navController.popBackStack(R.id.logInFragment, false)
                navController.navigate(R.id.logInFragment)
            }
            R.id.settings -> {
                supportActionBar!!.show()
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
                supportActionBar!!.show()
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