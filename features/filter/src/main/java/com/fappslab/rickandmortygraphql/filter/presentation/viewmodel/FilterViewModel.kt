package com.fappslab.rickandmortygraphql.filter.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.rickandmortygraphql.arch.viewmodel.ViewModel
import com.fappslab.rickandmortygraphql.domain.model.KeyType
import com.fappslab.rickandmortygraphql.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.filter.domain.usecase.DeleteFilterUseCase
import com.fappslab.rickandmortygraphql.filter.domain.usecase.SetFilterUseCase
import com.fappslab.rickandmortygraphql.filter.presentation.model.FilterMapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

internal class FilterViewModel(
    private val getFilterUseCase: GetFilterUseCase,
    private val setFilterUseCase: SetFilterUseCase,
    private val deleteFilterUseCase: DeleteFilterUseCase,
) : ViewModel<FilterViewState, FilterViewAction>(FilterViewState()) {

    fun getFilters() {
        viewModelScope.launch {
            KeyType.values().forEach { keyType ->
                getFilterUseCase(keyType.name).collect { filterName ->
                    onState { it.filterUpdate(keyType, filterName) }
                }
            }
        }
    }

    fun radioFilterBehavior(keyType: KeyType, idRadio: Int) {
        val filterFlow = FilterMapper.getFilterName(keyType, idRadio)?.let {
            setFilterUseCase(keyType.name, value = it)
        } ?: deleteFilterUseCase(keyType.name)

        filterFlow.launchIn(viewModelScope)
    }
}
