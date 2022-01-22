package com.nahlasamir244.taskhive.ui.task.views.deleteCompleted

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.nahlasamir244.taskhive.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteCompletedTasksFragment : DialogFragment() {

    private val viewModel: DeleteCompletedTasksViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.confirm_deletion)
            .setMessage(R.string.delete_all_completed_tasks_dialog_message)
            .setNegativeButton(R.string.cancel,null)
            .setPositiveButton(R.string.yes){ _,_ ->
                viewModel.onConfirmDeletionClicked()
            }
            .create()
}