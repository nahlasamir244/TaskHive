package com.nahlasamir244.taskhive.ui.task.views.addEdit

sealed class AddEditTaskEvent{
    data class ShowInvalidInputMessage(val message:String) :AddEditTaskEvent()
    data class NavigateBackToTasksWithResult(val result:Int): AddEditTaskEvent()
}
