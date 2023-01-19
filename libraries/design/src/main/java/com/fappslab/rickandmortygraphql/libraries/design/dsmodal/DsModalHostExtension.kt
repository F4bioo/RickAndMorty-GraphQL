package com.fappslab.rickandmortygraphql.libraries.design.dsmodal

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.rickandmortygraphql.arch.extension.isNotNull

private const val DS_MODAL_HOST_TAG = "DsModalHost"

@Suppress("unused")
fun LifecycleOwner.dsModalHost(
    block: DsModalHost.() -> Unit
): DsModalHost = DsModalHost().apply(block)

fun DsModalHost.build(manager: FragmentManager, tag: String = DS_MODAL_HOST_TAG) {
    takeIf {
        manager.findFragmentByTag(tag).isNotNull()
    } ?: show(manager, tag)
}
