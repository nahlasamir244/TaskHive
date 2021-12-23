package com.nahlasamir244.taskhive.ui.task.addEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.nahlasamir244.taskhive.R
import com.nahlasamir244.taskhive.databinding.FragmentAddEditTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddEditTaskFragment()
    }

    //delegate property to be injected by dagger
    private val viewModel: AddEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddEditTaskBinding.bind(view)
        binding.apply {
            textInputTaskName.setText(viewModel.taskName)
            checkboxTaskImportant.isChecked = viewModel.taskImportance
            checkboxTaskImportant.jumpDrawablesToCurrentState()
            textViewTaskDateModified.isVisible = viewModel.task != null
            textViewTaskDateModified.text = "Modified: ${viewModel.task?.dateModifiedFormatted}"
        }
    }

}