package com.nahlasamir244.taskhive.ui.task.views.tasks

import com.nahlasamir244.taskhive.data.model.Task

sealed class TasksEvent {
    data class ShowUndoDeleteTaskMessage(val deletedTask:Task) : TasksEvent()
    object NavigateToAddTask : TasksEvent()
    data class NavigateToEditTask(val task: Task) : TasksEvent()
    data class ShowTaskSavedConfirmationMessage(val messageResource:Int):TasksEvent()
}
