package com.onopry.ritmultipleapis.presentation.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

typealias OnCloseDialog = () -> Unit

class NationalizeDialogFragment : DialogFragment() {

    var data: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setMessage(data)
            .setPositiveButton("OK") { _, _ ->
                dismissNow()
            }
            .create()
    }
}