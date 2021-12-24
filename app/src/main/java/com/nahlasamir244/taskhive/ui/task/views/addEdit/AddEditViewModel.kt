package com.nahlasamir244.taskhive.ui.task.views.addEdit

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.repo.TaskRepository
import com.nahlasamir244.taskhive.utils.Constants
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    //we have two options to retrieve the navigation args : tha task to be edited in the edit case
    //pass it from the fragment itself to the viewmodel
    //use saved state handle to get the navArgs and it survive process death
    val task = state.get<Task>("task")
    var taskName = state.get<String>("taskName") ?: task?.name ?: ""
        set(value) {
            field = value
            state.set("taskName", value)
        }
    var taskImportance = state.get<Boolean>("taskImportance") ?: task?.important ?: false
        set(value) {
            field = value
            state.set("taskImportance", value)
        }
    private val addEditTaskEventChannel = Channel<AddEditTaskEvent>()
    val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

    fun onSaveTaskFabClicked() {
        if (isTaskInputValid()) {
            val result:Int
            //add or edit
            if (task == null) {
                //add
                val newTask = Task(taskName, taskImportance)
                result = Constants.ADD_TASK_RESULT_OK
                addTask(newTask)
            } else {
                //edit
                val updatedTask = task.copy(name = taskName, important = taskImportance)
                result = Constants.EDIT_TASK_RESULT_OK
                updateTask(updatedTask)
            }
            //after add or edit navigate back to tasks screen
            viewModelScope.launch {
                addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackToTasksWithResult(result))
            }
        } else {
            //show error message
            showInvalidInputMessage("Name cannot be empty ..! ")
            return
        }

    }

    private fun showInvalidInputMessage(message: String) {
        viewModelScope.launch {
            addEditTaskEventChannel.send(AddEditTaskEvent.ShowInvalidInputMessage(message))
        }

    }

    private fun isTaskInputValid(): Boolean = taskName.isNotBlank()
    private fun addTask(newTask: Task) = viewModelScope.launch {
        taskRepository.insert(newTask)
    }

    private fun updateTask(updatedTask: Task) = viewModelScope.launch {
        taskRepository.update(updatedTask)
    }

}