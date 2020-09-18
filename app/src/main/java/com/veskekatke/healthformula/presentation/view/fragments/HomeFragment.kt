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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.post.Post
import com.veskekatke.healthformula.presentation.view.recycler.adapter.PostAdapter
import com.veskekatke.healthformula.presentation.view.recycler.adapter.SupplementAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.PostDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.PostViewModel
import com.veskekatke.healthformula.presentation.viewmodel.SupplementViewModel
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
            val navController = findNavController()

            val jsonData = Gson().toJson(it)

            val action = HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(jsonData)
            navController.navigate(action)
        }
        postRv.adapter = postAdapter
    }

    private fun initObservers(){
        postViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            postAdapter.submitList(it)
        })
    }

}