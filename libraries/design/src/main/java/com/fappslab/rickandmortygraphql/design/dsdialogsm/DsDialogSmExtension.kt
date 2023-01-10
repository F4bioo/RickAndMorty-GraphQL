package com.fappslab.rickandmortygraphql.design.dsdialogsm

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.rickandmortygraphql.arch.extension.isNotNull

@Suppress("unused")
fun LifecycleOwner.dsDialogSm(
    block: DsDialogSm.() -> Unit = {}
): DsDialogSm = DsDialogSm().apply(block)


fun DsDialogSm.build(manager: FragmentManager) {
    takeIf {
        manager.findFragmentByTag(this::class.java.simpleName).isNotNull()
    } ?: show(manager, this::class.java.simpleName)
}
