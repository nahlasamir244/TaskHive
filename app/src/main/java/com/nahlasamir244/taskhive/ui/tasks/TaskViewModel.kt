package com.nahlasamir244.taskhive.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.preferences.TaskPreferencesManager
import com.nahlasamir244.taskhive.data.repo.TaskRepository
import com.nahlasamir244.taskhive.utils.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TaskViewModel @ViewModelInject constructor(
    private val taskRepository: TaskRepository,
    private val taskPreferencesManager: TaskPreferencesManager
) : ViewModel() {
    val taskPreferencesFlow = taskPreferencesManager.taskPreferencesFlow

    //stateflow is similar to livedata it can hold a single value not stream but it can be still used as flow
    var searchKeyWord = MutableStateFlow("")

    //    private val tasksFlow = searchKeyWord.flatMapLatest {
//        taskRepository.getTasks(it)
//    }

    //combine : takes multiple flows to single flow
    // and execute lambda whenever anyone of the flow emits new value
    private val tasksFlow = combine(
        searchKeyWord,
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

    }

    fun onTaskItemCompletedChecked(task: Task, checked: Boolean) =
        viewModelScope.launch {
            taskRepository.update(task.copy(completed = checked))
        }
    fun onTaskItemSwiped(task: Task){
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

}