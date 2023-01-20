package com.fappslab.rickandmortygraphql.libraries.design.dsdialogsm

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.rickandmortygraphql.libraries.arch.extension.isNotNull

private const val DS_DIALOG_SM_TAG = "DsDialogSm"

@Suppress("unused")
fun LifecycleOwner.dsDialogSm(
    block: DsDialogSm.() -> Unit = {}
): DsDialogSm = DsDialogSm().apply(block)

fun DsDialogSm.build(shouldShow: Boolean, manager: FragmentManager) {
    manager.hide()
    if (shouldShow) {
        show(manager, DS_DIALOG_SM_TAG)
    }
}

private fun FragmentManager.hide() {
    if (isShowing()) {
        val dialog = findFragmentByTag(DS_DIALOG_SM_TAG)
        if (dialog is DsDialogSm) dialog.dismissAllowingStateLoss()
    }
}

private fun FragmentManager.isShowing(): Boolean {
    executePendingTransactions()
    return findFragmentByTag(DS_DIALOG_SM_TAG).isNotNull()
}
