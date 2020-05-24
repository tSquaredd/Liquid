package com.tsquaredapplications.liquid.login.login

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.databinding.ForgotPasswordLayoutBinding

class ForgotPasswordDialog(private val authManager: AuthManager) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            with(ForgotPasswordLayoutBinding.inflate(inflater)) {
                sendButton.setOnClickListener {
                    authManager.sendPasswordReset(emailInputEditText.text.toString(),
                        onComplete = {
                            dismiss()
                        },
                        onSuccess = {
                            Toast.makeText(context, "Password reset sent", Toast.LENGTH_LONG).show()
                        },
                        onFailure = {
                            Toast.makeText(
                                context,
                                "Problem sending password reset",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                }
                cancelButton.setOnClickListener {
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