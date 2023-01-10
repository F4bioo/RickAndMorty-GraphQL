package com.fappslab.rickandmortygraphql.design.dsdialogsm

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.fappslab.rickandmortygraphql.design.R
import com.fappslab.rickandmortygraphql.design.databinding.LayoutDsdialogSmBinding

class DsDialogSm : DialogFragment(R.layout.layout_dsdialog_sm) {

    private lateinit var binding: LayoutDsdialogSmBinding

    @StringRes
    var titleRes: Int? = null
    var titleText: String? = null

    @StringRes
    var messageRes: Int? = null
    var messageText: String? = null

    var onCloseButton: () -> Unit? = { dismissAllowingStateLoss() }
    var onDismiss: () -> Unit? = { dismissAllowingStateLoss() }
    var gravityBottom: Boolean = true
    var shouldBlock: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LayoutDsdialogSmBinding.inflate(layoutInflater)
        return setupDialog(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBehavior()
        setupTitle()
        setupMessage()
        setupCloseButton()
        setupDialog(view)
    }

    private fun setupDialog(view: View): Dialog {
        AlertDialog.Builder(view.context).run {
            setView(view)
            return create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.gravityBottom(gravityBottom)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss()
    }

    private fun setupBehavior() {
        isCancelable = shouldBlock
    }

    private fun setupTitle() {
        binding.textTitle.apply {
            text = getText(titleRes, titleText)
            handleVisibility()
        }
    }

    private fun setupMessage() {
        binding.textMessage.apply {
            text = getText(messageRes, messageText)
            movementMethod = ScrollingMovementMethod()
            handleVisibility()
        }
    }

    private fun setupCloseButton() {
        binding.buttonClose.setOnClickListener {
            onCloseButton()
            dismiss()
        }
    }

    private fun TextView.handleVisibility() {
        isVisible = text.isNotEmpty()
    }

    private fun getText(contentRes: Int?, content: String?): String {
        contentRes?.let { return getString(it) }
        return content.orEmpty()
    }

    private fun Window?.gravityBottom(isGravityBottom: Boolean) {
        if (isGravityBottom) this?.setGravity(Gravity.BOTTOM)
    }
}
