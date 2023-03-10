package com.fappslab.rickandmortygraphql.features.details.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.fappslab.rickandmortygraphql.core.common.navigation.CharacterArgs
import com.fappslab.rickandmortygraphql.features.details.R
import com.fappslab.rickandmortygraphql.features.details.databinding.DetailsFragmentBinding
import com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel.DetailsViewModel
import com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel.DetailsViewState
import com.fappslab.rickandmortygraphql.libraries.arch.args.view.putArguments
import com.fappslab.rickandmortygraphql.libraries.arch.args.view.viewArgs
import com.fappslab.rickandmortygraphql.libraries.arch.args.view.withArgs
import com.fappslab.rickandmortygraphql.libraries.arch.viewbinding.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val binding: DetailsFragmentBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModel { parametersOf(args) }
    private val args: CharacterArgs by viewArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
    }

    private fun setupObservables() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.viewState()
                }
            }
        }
    }

    private fun DetailsViewState.viewState() = binding.run {
        imageCharacter.load(character.image)
        chipStatus.text = character.status
        textName.text = character.name
        textId.text = getString(R.string.details_character_id, character.id)
        includeDetails.textDetailsGenderContent.text = character.gender
        includeDetails.textDetailsSpeciesContent.text = character.species
        includeOrigin.textOriginNameContent.text = character.originName
        chipStatus.setChipBackgroundColorResource(statusType.colorRes)
        imageCharacter.setStrokeColorResource(statusType.colorRes)
    }

    companion object {
        fun createFragment(args: CharacterArgs): DetailsFragment =
            DetailsFragment().withArgs { putArguments(args) }
    }
}
