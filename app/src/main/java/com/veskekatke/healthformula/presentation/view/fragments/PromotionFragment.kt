package com.veskekatke.healthformula.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
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
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.activities.MainActivity
import com.veskekatke.healthformula.presentation.view.recycler.adapter.PostAdapter
import com.veskekatke.healthformula.presentation.view.recycler.adapter.SupplementAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.PostDiffItemCallback
import com.veskekatke.healthformula.presentation.view.states.PostsState
import com.veskekatke.healthformula.presentation.viewmodel.PostViewModel
import com.veskekatke.healthformula.presentation.viewmodel.SupplementViewModel
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_post_list_item.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class PromotionFragmentFragment : Fragment(R.layout.fragment_home), KoinComponent{

    private val postViewModel: MainContract.PostViewModel by sharedViewModel<PostViewModel> ()

    private val userViewModel : MainContract.UserViewModel by sharedViewModel<UserViewModel>()

    private val sharedPref : SharedPreferences by inject()

    private lateinit var postAdapter : PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.promotionFragmentFragment)
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
//
            val action = PromotionFragmentFragmentDirections.actionPromotionFragmentFragmentToPostDetailsFragment(jsonData)
            (requireActivity() as MainActivity).supportActionBar!!.hide()
            navController.navigate(action)
        }
        postRv.adapter = postAdapter
    }

    private fun initObservers(){
        (requireActivity() as MainActivity).userViewModel.fetch(sharedPref.getString("userId", "")!!)
        postViewModel.postsState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        postViewModel.getAllOnPromotion()
        //postViewModel.fetchAllPosts()
    }

    private fun renderState(state: PostsState) {
        when (state) {
            is PostsState.Success -> {
                showLoadingState(false)
                postAdapter.submitList(state.posts)
            }
            is PostsState.Error -> {
                showLoadingState(false)
                //Toast.makeText(context, state.message as String, Toast.LENGTH_SHORT).show()
            }
            is PostsState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is PostsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean){}

}