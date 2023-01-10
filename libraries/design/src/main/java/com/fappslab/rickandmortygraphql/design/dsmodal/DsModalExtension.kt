package com.fappslab.rickandmortygraphql.design.dsmodal

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.rickandmortygraphql.arch.extension.isNotNull

@Suppress("unused")
fun LifecycleOwner.dsModal(
    block: DsModal.() -> Unit
): DsModal = DsModal().apply(block)

fun DsModal.build(manager: FragmentManager) {
    takeIf {
        manager.findFragmentByTag(this::class.java.simpleName).isNotNull()
    } ?: show(manager, this::class.java.simpleName)
}
