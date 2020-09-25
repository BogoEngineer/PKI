package com.veskekatke.healthformula.presentation.view.states

import com.veskekatke.healthformula.data.models.supplement.Supplement

sealed class SupplementsState {
    object Loading: SupplementsState()
    object DataFetched: SupplementsState()
    data class Success(val supplements: List<Supplement>): SupplementsState()
    data class Error(val message: String): SupplementsState()
}