package com.veskekatke.healthformula.presentation.view.fragments

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.recycler.adapter.SupplementAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.SupplementDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.SupplementViewModel
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_mysupplementlist.*
import kotlinx.android.synthetic.main.fragment_postdetails.view.*
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MySupplementListFragment(private val list : List<Supplement>, private val title : String) : Fragment(R.layout.fragment_mysupplementlist){
    private var open : Boolean = false

    private lateinit var supplementAdapter : SupplementAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
        initListeners()
    }

    private fun initUI(){
        val sf = object: ShapeDrawable.ShaderFactory(){
            @RequiresApi(Build.VERSION_CODES.M)
            override fun resize(p0: Int, p1: Int): Shader {
                val lg = LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), postCard.height.toFloat(),
                    intArrayOf(
                        context!!.getColor(R.color.darkdarkblue),
                        context!!.getColor(R.color.darkblue),
                        context!!.getColor(R.color.blue),
                        context!!.getColor(R.color.lightblue),
                        context!!.getColor(R.color.lightlightblue)),
                floatArrayOf(0.toFloat(), 0.25f, 0.50f, 0.75f, 1.toFloat()),
                Shader.TileMode.REPEAT)
                return lg
            }

        }
        val p = PaintDrawable()
        p.shape = RectShape()
        p.shaderFactory = sf
        cardBackground.background = p as Drawable
        //postCard.background = p as Drawable
        supplementListTitleTv.text = "+ $title"
        mySupplementListRv.visibility = View.GONE
        initRecycler()
    }

    private fun initListeners(){
        supplementListTitleTv.setOnClickListener {
            open = !open
            supplementListTitleTv.text = if(!open) "+ $title" else "- $title"
            if(open){
                mySupplementListRv.visibility = View.VISIBLE
            }else mySupplementListRv.visibility = View.GONE
        }
    }

    private fun initRecycler(){
        mySupplementListRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        supplementAdapter = SupplementAdapter(true, SupplementDiffItemCallback()){
            Timber.e(it.toString())
        }
        mySupplementListRv.adapter = supplementAdapter

    }

    private fun initObservers(){
        supplementAdapter.submitList(list)
    }

}