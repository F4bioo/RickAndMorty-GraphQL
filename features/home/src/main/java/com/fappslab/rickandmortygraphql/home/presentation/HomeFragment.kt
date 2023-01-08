package com.fappslab.rickandmortygraphql.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.arch.viewmodel.onViewAction
import com.fappslab.rickandmortygraphql.arch.viewmodel.onViewState
import com.fappslab.rickandmortygraphql.home.R
import com.fappslab.rickandmortygraphql.home.databinding.HomeFragmentBinding
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewAction
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(R.layout.home_fragment) {

    //private val binding: HomeFragmentBinding by viewBinding()
    private val viewModel: HomeViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->

        }

        onViewAction(viewModel) { action ->
            when (action) {
                HomeViewAction.Refresh -> {}
                is HomeViewAction.Error -> {}
            }
        }
    }

    companion object {
        fun createFragment(): HomeFragment = HomeFragment()
    }
}
