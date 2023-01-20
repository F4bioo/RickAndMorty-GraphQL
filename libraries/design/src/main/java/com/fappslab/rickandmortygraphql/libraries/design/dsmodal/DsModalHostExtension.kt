package com.fappslab.rickandmortygraphql.libraries.design.dsmodal

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.rickandmortygraphql.libraries.arch.extension.isNotNull

private const val DS_MODAL_HOST_TAG = "DsModalHost"

@Suppress("unused")
fun LifecycleOwner.dsModalHost(
    block: DsModalHost.() -> Unit
): DsModalHost = DsModalHost().apply(block)

fun DsModalHost.build(shouldShow: Boolean, manager: FragmentManager) {
    manager.hide()
    if (shouldShow) {
        show(manager, DS_MODAL_HOST_TAG)
    }
}

private fun FragmentManager.hide() {
    if (isShowing()) {
        val dialog = findFragmentByTag(DS_MODAL_HOST_TAG)
        if (dialog is DsModalHost) dialog.dismissAllowingStateLoss()
    }
}

private fun FragmentManager.isShowing(): Boolean {
    executePendingTransactions()
    return findFragmentByTag(DS_MODAL_HOST_TAG).isNotNull()
}
