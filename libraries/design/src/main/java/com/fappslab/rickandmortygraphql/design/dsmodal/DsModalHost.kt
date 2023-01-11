package com.fappslab.rickandmortygraphql.design.dsmodal

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams.MATCH_PARENT
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fappslab.rickandmortygraphql.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.design.R
import com.fappslab.rickandmortygraphql.design.databinding.LayoutDsmodaHostBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.R as GM

class DsModalHost : BottomSheetDialogFragment(R.layout.layout_dsmoda_host) {

    private val binding: LayoutDsmodaHostBinding by viewBinding()

    var setFragment: (() -> Fragment)? = null
    var onCloseButton: () -> Unit? = { dismissAllowingStateLoss() }
    var onDismiss: () -> Unit? = { dismissAllowingStateLoss() }
    var shouldBlock: Boolean = false
    var shouldExpanded: Boolean = false

    override fun getTheme() = R.style.DsBottomSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHostHeight()
        setupBehavior()
        setupChild()
        setupCloseButton()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss()
    }

    private fun setupHostHeight() {
        (dialog?.findViewById(GM.id.design_bottom_sheet) as? FrameLayout)?.let { frameLayout ->
            frameLayout.layoutParams.height = MATCH_PARENT
        }
    }

    private fun setupBehavior() {
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            if (shouldExpanded) {
                state = BottomSheetBehavior.STATE_EXPANDED
            }
            if (shouldBlock) {
                isHideable = true
                isCancelable = false
                isDraggable = false
            }
        }
    }

    private fun setupCloseButton() {
        binding.buttonClose.setOnClickListener {
            onCloseButton()
            dismiss()
        }
    }

    private fun setupChild() {
        setFragment?.let { fragment ->
            childFragmentManager.commit { replace(binding.containerFragment.id, fragment()) }
        }
    }
}
