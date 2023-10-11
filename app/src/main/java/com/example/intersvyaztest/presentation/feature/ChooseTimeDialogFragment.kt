package com.example.intersvyaztest.presentation.feature

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.itntersvyaztest.R

class ChooseTimeDialogFragment : DialogFragment() {

    private val timeArray = arrayOf(
//        requireContext().getString(R.string.fifteen_minutes_text),
//        requireContext().getString(R.string.one_hour_text),
//        requireContext().getString(R.string.one_day_text),
//        requireContext().getString(R.string.seven_days_text)
        "15 minutes", "1 hour", "1 day", "7 days"
    )

    var onTimeSelectedListener: TimeSelectionListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(requireContext().getString(R.string.choose_period_text))
                .setItems(
                    timeArray
                ) { _, which ->
                    onTimeSelectedListener?.onTimeSelected(timeArray[which])
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface TimeSelectionListener {
        fun onTimeSelected(selectedTime: String)
    }

    companion object {
        const val TAG = "ChooseTimeDialogFragment_TAG"
    }
}