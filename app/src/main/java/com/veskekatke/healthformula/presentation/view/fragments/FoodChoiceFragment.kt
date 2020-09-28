package com.veskekatke.healthformula.presentation.view.fragments

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.user.UserResponse
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.recycler.adapter.FoodItemAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.FoodItemDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_allsupplements.*
import kotlinx.android.synthetic.main.fragment_foodchoice.*
import kotlinx.android.synthetic.main.fragment_fooditemdetails.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.util.*

class FoodChoiceFragment : Fragment(R.layout.fragment_foodchoice){
    private val userViewModel: MainContract.UserViewModel by sharedViewModel<UserViewModel>()

    private lateinit var foodItemAllowedAdapter: FoodItemAdapter
    private lateinit var foodItemNotAllowedAdapter: FoodItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
    }

//    override fun onBackPressed(){
//        if(foodItemDetails.isVisible){
//            foodItemDetails.visibility = View.GONE
//        }else{
//            requireActivity().onBackPressed()
//        }
//    }

    private fun fadeCard(allowed: Boolean, card: View){
        if(allowed){
            val sfN = object: ShapeDrawable.ShaderFactory(){
                @RequiresApi(Build.VERSION_CODES.M)
                override fun resize(p0: Int, p1: Int): Shader {
                    return LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), card.height.toFloat(),
                        intArrayOf(
                            requireActivity().getColor(R.color.greenFade1),
                            requireActivity().getColor(R.color.greenFade2),
                            requireActivity().getColor(R.color.greenFade3),
                            requireActivity().getColor(R.color.greenFade4),
                            requireActivity().getColor(R.color.greenFade5)),
                        floatArrayOf(0.20f, 0.40f, 0.50f, 0.75f, 1f),
                        Shader.TileMode.REPEAT)
                }

            }
            val pN = PaintDrawable()
            pN.shape = RectShape()
            pN.shaderFactory = sfN
            card.background = pN as Drawable
        }else{
            val sfN = object: ShapeDrawable.ShaderFactory(){
                @RequiresApi(Build.VERSION_CODES.M)
                override fun resize(p0: Int, p1: Int): Shader {
                    return LinearGradient(0.toFloat(),0.toFloat(),0.toFloat(), card.height.toFloat(),
                        intArrayOf(
                            requireActivity().getColor(R.color.redFade1),
                            requireActivity().getColor(R.color.redFade2),
                            requireActivity().getColor(R.color.redFade3),
                            requireActivity().getColor(R.color.redFade4),
                            requireActivity().getColor(R.color.redFade5)),
                        floatArrayOf(0.1f, 0.30f, 0.50f, 0.75f, 1f),
                        Shader.TileMode.REPEAT)
                }

            }
            val pN = PaintDrawable()
            pN.shape = RectShape()
            pN.shaderFactory = sfN
            card.background = pN as Drawable
        }
    }

    fun closeDetails(){
        foodItemDetails.visibility = View.GONE
    }

    private fun initUI(){
        childFragmentManager.beginTransaction().add(R.id.foodItemDetails, FoodItemDetailsFragment()).commit()
        foodItemDetails.visibility = View.GONE
        foodItemDetails.bringToFront()
        initRecycler()
    }

    private fun initRecycler(){
        allowedRv.layoutManager = LinearLayoutManager(this.context)
        foodItemAllowedAdapter = FoodItemAdapter(FoodItemDiffItemCallback()){
            (childFragmentManager.findFragmentById(R.id.foodItemDetails) as FoodItemDetailsFragment)
                .updateFoodItem(it)
            foodItemDetails.visibility = View.VISIBLE
            lists.isFocusable = false
            fadeCard(true, fadingCard)
        }
        allowedRv.adapter = foodItemAllowedAdapter

        notAllowedRv.layoutManager = LinearLayoutManager(this.context)
        foodItemNotAllowedAdapter = FoodItemAdapter(FoodItemDiffItemCallback()){
            (childFragmentManager.findFragmentById(R.id.foodItemDetails) as FoodItemDetailsFragment)
                .updateFoodItem(it)
            foodItemDetails.visibility = View.VISIBLE
            lists.isFocusable = false
            fadeCard(false, fadingCard)
        }
        notAllowedRv.adapter = foodItemNotAllowedAdapter
    }

    private fun initObservers(){
        userViewModel.user.observe(viewLifecycleOwner, Observer {
            Timber.e("OVDE")
            foodItemAllowedAdapter.submitList(it.phase.food_choice.allowed)
            foodItemNotAllowedAdapter.submitList(it.phase.food_choice.not_allowed)
        })
        userViewModel.get()

        searchFoodItemEt.doAfterTextChanged{
            val filter = it.toString().toLowerCase()
            if(filter == ""){
                userViewModel.get()
            }
            else{
                userViewModel.getFoodItemsByName(filter)
            }
        }
    }
}