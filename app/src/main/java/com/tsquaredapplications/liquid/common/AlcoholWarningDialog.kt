package com.tsquaredapplications.liquid.common

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tsquaredapplications.liquid.databinding.AlcoholWarningBinding

class AlcoholWarningDialog(val onDismiss: (Boolean) -> Unit) : DialogFragment() {
    private var doNotShowAgain = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            with(AlcoholWarningBinding.inflate(inflater)) {
                dismissButton.setOnClickListener {
                    onDismiss(doNotShowAgain)
                    dismiss()
                }
                dontShowAgainCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    doNotShowAgain = isChecked
                }

                builder.setView(root)
                builder.create()
            }
        } ?: throw IllegalStateException("Activity cannot be null")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}