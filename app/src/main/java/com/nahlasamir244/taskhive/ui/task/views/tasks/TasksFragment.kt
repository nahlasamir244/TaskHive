package com.nahlasamir244.taskhive.ui.task.views.tasks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nahlasamir244.taskhive.R
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.databinding.FragmentTasksBinding
import com.nahlasamir244.taskhive.ui.task.adapter.TasksAdapter
import com.nahlasamir244.taskhive.ui.task.adapter.TasksAdapterEventHandler
import com.nahlasamir244.taskhive.utils.Constants
import com.nahlasamir244.taskhive.utils.SortType
import com.nahlasamir244.taskhive.utils.exhaustive
import com.nahlasamir244.taskhive.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//to use viewModel annotated with @viewModelInject
//have to annotate fragment or activity with @AndroidEntryPoint
@AndroidEntryPoint
class TasksFragment : Fragment() ,TasksAdapterEventHandler {

    companion object {
        fun newInstance() = TasksFragment()
    }

    //delegate property to be injected by dagger
    private val viewModel: TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTasksBinding.bind(view)
        val tasksAdapter = TasksAdapter(this)
        binding.apply {
            recyclerViewTasks.apply {
                adapter = tasksAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true) // if layout doesnt change its dimension on screen
            }
            //takes callback to be executed when you swipe or drag item
            //simple callback takes two directions to support (drag and swipe)
            ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
            or ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                   //here you can add drag and drop logic , not needed in our case
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val currentTask = tasksAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onTaskItemSwiped(currentTask)
                }

            }).attachToRecyclerView(recyclerViewTasks)
            fabAddTask.setOnClickListener {
                viewModel.onAddTaskFabClicked()
            }
        }
        setFragmentResultListener(Constants.ADD_EDIT_TASK_REQUEST_KEY){
            _,bundle ->
                val result = bundle.getInt(Constants.ADD_EDIT_TASK_RESULT_KEY)
            viewModel.onAddEditResult(result)
        }

        viewModel.taskList.observe(viewLifecycleOwner){
            tasksAdapter.submitList(it)
        }
        setHasOptionsMenu(true)
        //launchWhenStarted() cancelled when onStop is called
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
             viewModel.tasksEvent.collect {
                 event ->
                 when(event){
                      is TasksEvent.ShowUndoDeleteTaskMessage -> {
                         Snackbar.make(view,"Task ${event.deletedTask.name} is deleted",Snackbar.LENGTH_LONG).setAction(
                             R.string.undo
                         ) {
                             viewModel.onUndoDeleteClicked(event.deletedTask)
                         }.show()
                     }
                     is TasksEvent.NavigateToAddTask -> {
                         findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(title = "New Task"))
                     }
                     is TasksEvent.NavigateToEditTask -> {
                         findNavController().navigate(
                             TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment
                                 (event.task,"Edit Task"))
                     }
                     is TasksEvent.ShowTaskSavedConfirmationMessage ->
                     Snackbar.make(requireView(), event.messageResource, Snackbar.LENGTH_SHORT).show()
                     TasksEvent.NavigateToDeleteCompletedTasks ->
                         findNavController().navigate(TasksFragmentDirections.actionGlobalDeleteCompletedTasksFragment())
                 }.exhaustive
             }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks,menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryTextChanged {
            viewModel.searchKeyWord.value = it
        }
        viewLifecycleOwner.lifecycleScope.launch {
            //we use first instead of flow cause we want to read one value once the app started
            //and dont get any update from collect whenever the value changes
            menu.findItem(R.id.action_hide_completed_tasks).isChecked =
                viewModel.taskPreferencesFlow.first().hideCompleted
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_sort_by_name -> {
                viewModel.onSortTypeSelected(SortType.BY_NAME)
                true
            }
            R.id.action_sort_by_date_created -> {
                viewModel.onSortTypeSelected(SortType.BY_DATE)
                true
            }
            R.id.action_hide_completed_tasks -> {
                item.isChecked = !item.isChecked
                viewModel.onHideCompletedClicked(item.isChecked)
                true
            }
            R.id.action_delete_completed_tasks -> {
                viewModel.onDeleteCompletedTasksClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onTaskItemClicked(task: Task) {
        viewModel.onTaskItemClicked(task)
    }

    override fun onTaskItemCompletedChecked(task: Task, isChecked: Boolean) {
        viewModel.onTaskItemCompletedChecked(task,isChecked)
    }

}