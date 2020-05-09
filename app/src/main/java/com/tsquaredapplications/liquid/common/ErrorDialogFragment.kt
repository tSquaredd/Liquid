package com.tsquaredapplications.liquid.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tsquaredapplications.liquid.databinding.ErrorMessageLayoutBinding

class ErrorDialogFragment(private val errorMessage: String, private val dismissButtonText: String) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            with(ErrorMessageLayoutBinding.inflate(inflater)) {
                errorMessageTextView.text = errorMessage
                errorMessageDismissButton.apply {
                    setOnClickListener {
                        dismiss()
                    }
                    text = dismissButtonText
                }

                builder.setView(root)
                builder.create()
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}