package com.fappslab.rickandmortygraphql.libraries.design.dsdialogsm

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.rickandmortygraphql.arch.extension.isNotNull

private const val DS_DIALOG_SM_TAG = "DsDialogSm"

@Suppress("unused")
fun LifecycleOwner.dsDialogSm(
    block: DsDialogSm.() -> Unit = {}
): DsDialogSm = DsDialogSm().apply(block)


fun DsDialogSm.build(manager: FragmentManager, tag: String = DS_DIALOG_SM_TAG) {
    takeIf {
        manager.findFragmentByTag(tag).isNotNull()
    } ?: show(manager, tag)
}
