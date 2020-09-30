package com.veskekatke.healthformula.presentation.view.fragments

import android.content.SharedPreferences
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.presentation.view.recycler.adapter.SupplementAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.SupplementDiffItemCallback
import kotlinx.android.synthetic.main.fragment_mysupplementlist.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class MySupplementListFragment(private val list : List<Supplement> = listOf(), private val title : String = "") : Fragment(R.layout.fragment_mysupplementlist), KoinComponent{
    private var open : Boolean = false

    private lateinit var supplementAdapter : SupplementAdapter

    private val sharedPref : SharedPreferences by inject()

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
        val sfMs = object: ShapeDrawable.ShaderFactory(){
            @RequiresApi(Build.VERSION_CODES.M)
            override fun resize(p0: Int, p1: Int): Shader {
                return LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), postCard.height.toFloat(),
                    intArrayOf(
                        context!!.getColor(R.color.darkdarkblue),
                        context!!.getColor(R.color.darkblue),
                        context!!.getColor(R.color.blue),
                        context!!.getColor(R.color.lightblue),
                        context!!.getColor(R.color.lightlightblue)),
                floatArrayOf(0.toFloat(), 0.25f, 0.50f, 0.75f, 1.toFloat()),
                Shader.TileMode.REPEAT)
            }

        }
        val pMs = PaintDrawable()
        pMs.shape = RectShape()
        pMs.shaderFactory = sfMs
        cardBackground.background = pMs as Drawable
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
        val sp_list = sharedPref.getString(title, "")
        val checked_list = if(sp_list!="") Gson().fromJson(sp_list, MutableList::class.java) else mutableListOf(String)
        supplementAdapter = SupplementAdapter(true, SupplementDiffItemCallback(), checked_list as MutableList<String>){
            //Timber.e(it.toString())
            if(!checked_list.contains(it.name)) checked_list.add(it.name)
            else checked_list.remove(it.name)
            with(sharedPref.edit()){
                putString(title, Gson().toJson(list))
                commit()
            }
            Timber.e(checked_list.toString())
        }
        mySupplementListRv.adapter = supplementAdapter

    }

    private fun initObservers(){
        supplementAdapter.submitList(list)
    }
}