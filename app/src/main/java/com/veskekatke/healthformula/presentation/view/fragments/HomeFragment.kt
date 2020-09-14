package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.view.recycler.adapter.PostAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.PostDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home){

    private val postViewModel: PostViewModel by viewModels()

    private lateinit var postAdapter : PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.home)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
    }

    private fun initUI(){
        initRecycler()
    }

    private fun initRecycler(){
        postRv.layoutManager = LinearLayoutManager(this.context)
        postAdapter = PostAdapter(PostDiffItemCallback()){
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
        postRv.adapter = postAdapter
    }

    private fun initObservers(){
        postViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            postAdapter.submitList(it)
        })
    }

}