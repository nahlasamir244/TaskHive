package com.nahlasamir244.taskhive.ui.task.tasks

import com.nahlasamir244.taskhive.data.model.Task

sealed class TasksEvent {
    data class ShowUndoDeleteTasksMessage(val deletedTask:Task) : TasksEvent()
}
