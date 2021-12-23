package com.nahlasamir244.taskhive.ui.task.views.tasks

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.preferences.TaskPreferencesManager
import com.nahlasamir244.taskhive.data.repo.TaskRepository
import com.nahlasamir244.taskhive.ui.task.event.TaskEvent
import com.nahlasamir244.taskhive.utils.SortType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository,
    private val taskPreferencesManager: TaskPreferencesManager,
    @Assisted private val state:SavedStateHandle
) : ViewModel() {
    val taskPreferencesFlow = taskPreferencesManager.taskPreferencesFlow

    //channel hold data of type TaskEvent to be listened in fragment
    private val tasksEventChannel = Channel<TaskEvent>()
    //public property to access the private property
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    //stateflow is similar to livedata it can hold a single value not stream but it can be still used as flow
    //var searchKeyWord = MutableStateFlow("")
    //getLiveData in savedstatehandle gives you live updates no need to set
    var searchKeyWord = state.getLiveData("searchKeyword","")

    //    private val tasksFlow = searchKeyWord.flatMapLatest {
//        taskRepository.getTasks(it)
//    }

    //combine : takes multiple flows to single flow
    // and execute lambda whenever anyone of the flow emits new value
    private val tasksFlow = combine(
        searchKeyWord.asFlow(),
        taskPreferencesFlow
    ) { keyword, taskPreferences ->
        Pair(keyword, taskPreferences)
    }.flatMapLatest { (keyword, taskPreferences) ->
        taskRepository.getTasksSorted(keyword, taskPreferences.sortType,taskPreferences.hideCompleted)
    }

    //livedata can be observed as the single value not stream and it's lifecycle aware
    //flow has operators and can switch threads
    val taskList = tasksFlow.asLiveData()

    fun onSortTypeSelected(sortType: SortType) = viewModelScope.launch {
        taskPreferencesManager.updateSortTypePreferences(sortType)
    }

    fun onHideCompletedClicked(hideCompleted:Boolean) = viewModelScope.launch {
        taskPreferencesManager.updateHideCompletedPreferences(hideCompleted)
    }

    fun onTaskItemClicked(task: Task) {
        state.apply {
            set("task",task)
            set("taskName",task.name)
            set("taskImportance",task.important)
        }
        viewModelScope.launch {
            tasksEventChannel.send(TaskEvent.NavigateToEditTask(task))
        }

    }

    fun onTaskItemCompletedChecked(task: Task, checked: Boolean) =
        viewModelScope.launch {
            taskRepository.update(task.copy(completed = checked))
        }
    fun onTaskItemSwiped(task: Task){
        viewModelScope.launch {
            taskRepository.delete(task)
            tasksEventChannel.send(TaskEvent.ShowUndoDeleteTaskMessage(task))
        }
    }

    fun onUndoDeleteClicked(deletedTask: Task) {
        viewModelScope.launch {
            taskRepository.insert(deletedTask)
        }
    }

    fun onAddTaskFabClicked(){
        viewModelScope.launch {
            tasksEventChannel.send(TaskEvent.NavigateToAddTask)
        }

    }


}