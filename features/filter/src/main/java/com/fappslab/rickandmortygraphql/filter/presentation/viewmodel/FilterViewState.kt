package com.fappslab.rickandmortygraphql.filter.presentation.viewmodel

import com.fappslab.rickandmortygraphql.domain.model.KeyType
import com.fappslab.rickandmortygraphql.filter.R
import com.fappslab.rickandmortygraphql.filter.presentation.model.FilterMapper

internal data class FilterViewState(
    val idRadioStatus: Int = R.id.radio_no_status,
    val idRadioGender: Int = R.id.radio_no_gender,
    val idRadioSpecies: Int = R.id.radio_no_species
) {

    fun filterUpdate(keyType: KeyType, filterName: String?): FilterViewState {
        val id = FilterMapper.getId(keyType, filterName)
        return when (keyType) {
            KeyType.KeyStatus -> copy(idRadioStatus = id ?: R.id.radio_no_status)
            KeyType.KeyGender -> copy(idRadioGender = id ?: R.id.radio_no_gender)
            else -> copy(idRadioSpecies = id ?: R.id.radio_no_species)
        }
    }
}
