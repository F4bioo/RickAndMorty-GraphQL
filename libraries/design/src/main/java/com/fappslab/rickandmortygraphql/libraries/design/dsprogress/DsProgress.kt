package com.fappslab.rickandmortygraphql.libraries.design.dsprogress

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.fappslab.rickandmortygraphql.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.libraries.design.R
import com.fappslab.rickandmortygraphql.libraries.design.databinding.LayoutDsprogressBinding

class DsProgress : DialogFragment(R.layout.layout_dsprogress) {

    private val binding: LayoutDsprogressBinding by viewBinding()

    var buttonCancel: () -> Unit? = {}
    var shouldShowButtonCancel: Boolean = true
    var shouldShowMessage: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCancelButton()
        setupMessage()
        setupDialog(view)
    }

    private fun setupDialog(view: View): Dialog {
        AlertDialog.Builder(view.context).run {
            setView(view)
            val builder = create()
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return builder
        }
    }

    override fun onResume() {
        isCancelable = false
        super.onResume()
    }

    private fun setupCancelButton() {
        binding.dsButtonCancel.apply {
            isVisible = shouldShowButtonCancel
        }.setOnClickListener {
            buttonCancel.invoke()
            dismiss()
        }
    }

    private fun setupMessage() {
        binding.dsTextLoading.isVisible = shouldShowMessage
    }
}
