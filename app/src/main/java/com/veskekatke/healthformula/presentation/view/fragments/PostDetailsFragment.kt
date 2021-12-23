package com.veskekatke.healthformula.presentation.view.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.post.Post
import com.veskekatke.healthformula.data.repositories.PostRepositoryImpl
import com.veskekatke.healthformula.data.repositories.Recommendation
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.activities.MainActivity
import com.veskekatke.healthformula.presentation.view.recycler.adapter.CommentAdapter
import com.veskekatke.healthformula.presentation.view.recycler.adapter.PostAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.CommentDiffItemCallback
import com.veskekatke.healthformula.presentation.view.recycler.diff.PostDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_postdetails.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class PostDetailsFragment : Fragment(R.layout.fragment_postdetails) {

    private lateinit var args : PostDetailsFragmentArgs
    private lateinit var commentAdapter : CommentAdapter

    private val postViewModel: MainContract.PostViewModel by sharedViewModel<PostViewModel> ()

    private lateinit var post : Post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        recommendB.setOnClickListener{
            recommend()
        }
        val post_json = PostDetailsFragmentArgs.fromBundle(requireArguments()).post
        post = Gson().fromJson<Post>(post_json, Post::class.java)
        toolbar.title = post.name + " by " + post.author + " (" + post.pages + " pages" + ")"

        Picasso
            .get()
            .load(post.picture)
            .into(image)

        description.text = post.content

        // init recycler
        commentRv.layoutManager = LinearLayoutManager(this.context)
        commentAdapter = CommentAdapter(CommentDiffItemCallback() ){
        }
        commentAdapter.submitList(post.comments)
        commentRv.adapter = commentAdapter
        app_bar_layout.setTouchscreenBlocksFocus(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            findViewById(R.id.app_bar_layout).setKeyboardNavigationCluster(false);
//        }
    }

    private fun recommend(){
        (activity as MainActivity).getSharedPreferences()?.getString("user", "korisnik")?.let {
            val recomm = Recommendation(post.id, recommendT.text.toString(),
                it
            )
            postViewModel.addRecommendation(recomm)
            Toast.makeText(context, "You recommended a book!", Toast.LENGTH_LONG).show()
        }
    }
}
