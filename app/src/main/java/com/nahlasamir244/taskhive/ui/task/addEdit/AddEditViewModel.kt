package com.nahlasamir244.taskhive.ui.task.addEdit

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.repo.TaskRepository

class AddEditViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository,
    @Assisted private val state : SavedStateHandle
) :ViewModel() {
    //we have two options to retrieve the navigation args : tha task to be edited in the edit case
    //pass it from the fragment itself to the viewmodel
    //use saved state handle to get the navArgs and it survive process death
    val task = state.get<Task>("task")
    var taskName = state.get<String>("taskName") ?: task?.name ?: ""
    set(value) {
        field = value
        state.set("taskName",value)
    }
    var taskImportance = state.get<Boolean>("taskImportance") ?: task?.important ?: false
        set(value) {
            field = value
            state.set("taskImportance",value)
        }
}