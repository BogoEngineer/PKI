package com.veskekatke.healthformula.presentation.view.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.post.Post
import kotlinx.android.synthetic.main.fragment_postdetails.*
import timber.log.Timber

class PostDetailsFragment : Fragment(R.layout.fragment_postdetails) {

    private lateinit var args : PostDetailsFragmentArgs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val post_json = PostDetailsFragmentArgs.fromBundle(requireArguments()).post
        val post = Gson().fromJson<Post>(post_json, Post::class.java)
        toolbar.title = post.name

        Picasso
            .get()
            .load(post.picture)
            .into(image)

        description.text = post.content
        title.text = "RANDOM"
    }

    private fun initListeners(){
    }
}
