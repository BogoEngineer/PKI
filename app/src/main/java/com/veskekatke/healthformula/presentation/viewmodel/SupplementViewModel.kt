package com.veskekatke.healthformula.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veskekatke.healthformula.data.models.supplement.Supplement

class SupplementViewModel : ViewModel(){

    private val supplements : MutableLiveData<List<Supplement>> = MutableLiveData()

    private val mySupplementLists : MutableLiveData<List<List<Supplement>>> = MutableLiveData()

    private val supplementList : MutableList<Supplement> = mutableListOf<Supplement>()

    init {
        for (i in 1..100) {
            val supp = Supplement(
                i,
                "Ime $i",
                "Neki baja",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "https://lithub.com/wp-content/uploads/2019/03/pills.jpg"
            )
            supplementList.add(supp)
        }

        val listToSubmit = mutableListOf<Supplement>()
        listToSubmit.addAll(supplementList)
        supplements.value = listToSubmit

        val bigDummyList = mutableListOf<List<Supplement>>()
        for (i in 0..7){
            val dummyList = mutableListOf<Supplement>()
            for (j in 1..5){
                val supp = Supplement(
                    j,
                    "Ime $j",
                    "Neki baja $j",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    "https://lithub.com/wp-content/uploads/2019/03/pills.jpg"
                )
                dummyList.add(supp)
            }
            bigDummyList.add(dummyList)
        }
        mySupplementLists.value = bigDummyList
    }

    fun getAllSupplements() : LiveData<List<Supplement>> {
        return supplements
    }

    fun getMySupplements() : LiveData<List<List<Supplement>>>{
        return mySupplementLists
    }
}