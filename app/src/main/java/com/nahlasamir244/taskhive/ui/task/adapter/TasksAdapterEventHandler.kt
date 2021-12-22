package com.nahlasamir244.taskhive.ui.task.adapter

import com.nahlasamir244.taskhive.data.model.Task

interface TasksAdapterEventHandler {
    fun onTaskItemClicked(task:Task)
    fun onTaskItemCompletedChecked(task: Task,isChecked:Boolean)
}