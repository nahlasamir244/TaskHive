package com.nahlasamir244.taskhive.ui.task.views.addEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nahlasamir244.taskhive.R
import com.nahlasamir244.taskhive.databinding.FragmentAddEditTaskBinding
import com.nahlasamir244.taskhive.utils.Constants
import com.nahlasamir244.taskhive.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
            textInputTaskName.addTextChangedListener {
                viewModel.taskName = it.toString()
            }
            checkboxTaskImportant.setOnCheckedChangeListener { _, isChecked ->
                viewModel.taskImportance = isChecked
            }
            fabSaveTask.setOnClickListener {
                viewModel.onSaveTaskFabClicked()
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditTaskEvent.collect { event ->
                when(event){
                    is AddEditTaskEvent.NavigateBackToTasksWithResult -> {
                        binding.textInputTaskName.clearFocus()
                        setFragmentResult(Constants.ADD_EDIT_TASK_REQUEST_KEY,
                            bundleOf(Constants.ADD_EDIT_TASK_RESULT_KEY to event.result))
                        findNavController().popBackStack()
                    }
                    is AddEditTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(view,event.message,Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }


    }

}