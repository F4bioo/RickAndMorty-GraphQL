package com.fappslab.rickandmortygraphql.features.filter.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.libraries.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.libraries.arch.viewmodel.onViewAction
import com.fappslab.rickandmortygraphql.libraries.arch.viewmodel.onViewState
import com.fappslab.rickandmortygraphql.libraries.design.dsmodal.DsModalHost
import com.fappslab.rickandmortygraphql.core.common.domain.model.KeyType
import com.fappslab.rickandmortygraphql.features.filter.R
import com.fappslab.rickandmortygraphql.features.filter.databinding.FilterFragmentBinding
import com.fappslab.rickandmortygraphql.features.filter.presentation.viewmodel.FilterViewAction
import com.fappslab.rickandmortygraphql.features.filter.presentation.viewmodel.FilterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FilterFragment : Fragment(R.layout.filter_fragment) {

    private val binding: FilterFragmentBinding by viewBinding()
    private val viewModel: FilterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFilters()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            binding.run {
                includeStatus.groupStatus.check(state.idRadioStatus)
                includeGender.groupGender.check(state.idRadioGender)
                includeSpecies.groupSpecies.check(state.idRadioSpecies)
            }
        }

        onViewAction(viewModel) { action ->
            when (action) {
                FilterViewAction.Done -> (parentFragment as? DsModalHost)?.dismiss()
            }
        }
    }

    private fun setupListeners() = binding.run {
        includeStatus.groupStatus.setOnCheckedChangeListener { _, checkedId ->
            viewModel.radioFilterBehavior(KeyType.KeyStatus, checkedId)
        }

        includeGender.groupGender.setOnCheckedChangeListener { _, checkedId ->
            viewModel.radioFilterBehavior(KeyType.KeyGender, checkedId)
        }

        includeSpecies.groupSpecies.setOnCheckedChangeListener { _, checkedId ->
            viewModel.radioFilterBehavior(KeyType.KeySpecies, checkedId)
        }
    }

    companion object {
        fun create(): FilterFragment = FilterFragment()
    }
}
