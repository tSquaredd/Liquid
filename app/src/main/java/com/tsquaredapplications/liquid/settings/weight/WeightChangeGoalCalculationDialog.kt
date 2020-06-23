package com.tsquaredapplications.liquid.settings.weight

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tsquaredapplications.liquid.databinding.GoalCalculationPromptBinding

class WeightChangeGoalCalculationDialog(
    private val onConfirm: () -> Unit,
    private val onDecline: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            with(GoalCalculationPromptBinding.inflate(inflater)) {
                confirmButton.setOnClickListener {
                    onConfirm()
                    dismiss()
                }
                declineButton.setOnClickListener {
                    onDecline()
                    dismiss()
                }

                builder.setView(root)
                builder.create()
            }
        } ?: throw  IllegalStateException("Activity cannot be null")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}