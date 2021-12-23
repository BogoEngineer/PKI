package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.recycler.adapter.SupplementAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.SupplementDiffItemCallback
import com.veskekatke.healthformula.presentation.view.states.SupplementsState
import com.veskekatke.healthformula.presentation.viewmodel.SupplementViewModel
import kotlinx.android.synthetic.main.fragment_allsupplements.*
import okhttp3.internal.wait
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class AllSupplementsFragment : Fragment(R.layout.fragment_allsupplements){

    private lateinit var currentSupplement : Supplement

    private val supplementViewModel : MainContract.SupplementViewModel by sharedViewModel<SupplementViewModel> ()

    private lateinit var supplementAdapter : SupplementAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.allSupplements)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
        initListeners()
    }

    private fun initUI(){
        initRecycler()
    }

    private fun initRecycler(){
        supplementRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        supplementAdapter = SupplementAdapter(false, SupplementDiffItemCallback()){}
        supplementRv.adapter = supplementAdapter
    }

    private fun initListeners(){
        supplementRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                if(position == -1) return
                val foundSupplement = (recyclerView.adapter as SupplementAdapter).currentList[position]
                if(currentSupplement.id == foundSupplement.id) return
                currentSupplement = foundSupplement
                /*requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.supplementDetailsFr, SupplementDetailsFragment(currentSupplement))
                    .commit()*/

                (requireActivity().supportFragmentManager.findFragmentById(R.id.supplementDetailsFr) as SupplementDetailsFragment)
                    .updateSupplement(currentSupplement)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        searchSupplementEt.doAfterTextChanged {
            val filter = it.toString()
            supplementViewModel.getSupplementsByName(filter)
        }
    }

    private fun initObservers(){
        supplementViewModel.supplementsState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        supplementViewModel.getAllSupplements()
        supplementViewModel.fetchAllSupplements()
    }

    private fun renderState(state: SupplementsState) {
        when (state) {
            is SupplementsState.Success -> {
                showLoadingState(false)
                supplementAdapter.submitList(state.supplements)
                if(state.supplements.isNotEmpty())
                {
                    currentSupplement = state.supplements[0]
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.supplementDetailsFr, SupplementDetailsFragment(currentSupplement))
                        .commit()
                }
            }
            is SupplementsState.Error -> {
                showLoadingState(false)
                //Toast.makeText(context, state.message as String, Toast.LENGTH_SHORT).show()
            }
            is SupplementsState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SupplementsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean){}
}