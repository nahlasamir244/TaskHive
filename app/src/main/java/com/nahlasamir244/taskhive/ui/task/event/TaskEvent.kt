package com.nahlasamir244.taskhive.ui.task.event

import com.nahlasamir244.taskhive.data.model.Task

sealed class TaskEvent {
    data class ShowUndoDeleteTaskMessage(val deletedTask:Task) : TaskEvent()
    object NavigateToAddTask : TaskEvent()
    data class NavigateToEditTask(val task: Task) : TaskEvent()
}
