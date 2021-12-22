package com.nahlasamir244.taskhive.ui.tasks.adapter

import com.nahlasamir244.taskhive.data.model.Task

interface TasksAdapterEventHandler {
    fun onTaskItemClicked(task:Task)
    fun onTaskItemCompletedChecked(task: Task,isChecked:Boolean)
}