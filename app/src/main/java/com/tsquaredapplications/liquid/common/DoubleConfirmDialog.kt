package com.tsquaredapplications.liquid.common

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tsquaredapplications.liquid.databinding.DoubleConfirmDeleteLayoutBinding

class DoubleConfirmDialog(private val warningText: String, private val onConfirm: () -> Unit) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            with(DoubleConfirmDeleteLayoutBinding.inflate(inflater)) {
                warning.text = warningText
                cancelButton.setOnClickListener { dismiss() }
                confirmButton.setOnClickListener {
                    onConfirm()
                    dismiss()
                }

                builder.setView(root)
                builder.create()
            }
        } ?: throw IllegalStateException("Activity can not be null")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}