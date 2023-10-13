package com.example.intersvyaztest.presentation.feature

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.intersvyaztest.domain.CoinInfo
import com.example.intersvyaztest.presentation.CoinViewModel
import com.example.itntersvyaztest.R
import kotlinx.coroutines.launch

class EditDescriptionDialogFragment(
    private val item: CoinInfo
) : DialogFragment() {

    private lateinit var viewModel: CoinViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel =
            ViewModelProvider(this@EditDescriptionDialogFragment)[CoinViewModel::class.java]

        val context = requireContext()
        val dialog = AlertDialog.Builder(context)
        val input = EditText(context)
        dialog.setView(input)
        dialog.setTitle(getString(R.string.enter_description_text))

        dialog.setPositiveButton(getString(R.string.ok_notification_button_text)) { _, _ ->
            val newDescription = input.text.toString()

            lifecycleScope.launch {
                viewModel.updateCoinInfo(item.fromSymbol, item.isFavorite, newDescription)
            }

            dismiss()
        }

        dialog.setNegativeButton(getString(R.string.cancel_button_text)) { dialog, _ ->
            dialog.dismiss()
        }

        return dialog.create()
    }

    companion object {
        const val TAG = "EditDescriptionDialogFragment_TAG"
    }
}
