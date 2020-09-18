package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.view.recycler.adapter.SupplementAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.SupplementDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.SupplementViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_mysupplementlist.*
import timber.log.Timber

class MySupplementListFragment(private val list_id : Int, private val title : String) : Fragment(R.layout.fragment_mysupplementlist){
    private var open : Boolean = false

    private val supplementViewModel : SupplementViewModel by viewModels()

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
        supplementViewModel.getMySupplements().observe(viewLifecycleOwner, Observer {
            supplementAdapter.submitList(it[list_id])
        })
    }

}