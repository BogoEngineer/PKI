package com.veskekatke.healthformula.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veskekatke.healthformula.data.models.foodItem.FoodItemResponse

class FoodItemViewModel : ViewModel(){

    private val foodItemsAllowed : MutableLiveData<List<FoodItemResponse>> = MutableLiveData()

    private val foodItemsNotAllowed : MutableLiveData<List<FoodItemResponse>> = MutableLiveData()

    private val foodItemList : MutableList<FoodItemResponse> = mutableListOf()

    init {
        for (i in 1..100) {
            val foodItem = FoodItemResponse(
                i.toString(),
                "Ime $i",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                ""
            )
            foodItemList.add(foodItem)
        }
        val listToSubmit = mutableListOf<FoodItemResponse>()
        listToSubmit.addAll(foodItemList)
        foodItemsAllowed.value = listToSubmit
        foodItemsNotAllowed.value = listToSubmit.asReversed()
    }

    fun getAllowed() : LiveData<List<FoodItemResponse>> {
        return foodItemsAllowed
    }

    fun getNotAllowed() : LiveData<List<FoodItemResponse>> {
        return foodItemsNotAllowed
    }
}