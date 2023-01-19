package com.fappslab.rickandmortygraphql.libraries.arch.extension

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.setOnBackPressedDispatcher(onBackPressedBlock: () -> Unit) {
    object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedBlock()
        }
    }.also { activity?.onBackPressedDispatcher?.addCallback(it) }
}
