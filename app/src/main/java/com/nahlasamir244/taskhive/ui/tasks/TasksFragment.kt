package com.nahlasamir244.taskhive.ui.tasks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nahlasamir244.taskhive.R
import com.nahlasamir244.taskhive.databinding.FragmentTasksBinding
import com.nahlasamir244.taskhive.utils.SortType
import com.nahlasamir244.taskhive.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

//to use viewModel annotated with @viewModelInject
//have to annotate fragment or activity with @AndroidEntryPoint
@AndroidEntryPoint
class TasksFragment : Fragment() {

    companion object {
        fun newInstance() = TasksFragment()
    }

    //delegate property to be injected by dagger
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTasksBinding.bind(view)
        val tasksAdapter = TasksAdapter()
        binding.apply {
            recyclerViewTasks.apply {
                adapter = tasksAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true) // if layout doesnt change its dimension on screen
            }
        }

        viewModel.taskList.observe(viewLifecycleOwner){
            tasksAdapter.submitList(it)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks,menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryTextChanged {
            viewModel.searchKeyWord.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_sort_by_name -> {
                viewModel.tasksSortType.value = SortType.BY_NAME
                true
            }
            R.id.action_sort_by_date_created -> {
                viewModel.tasksSortType.value = SortType.BY_DATE
                true
            }
            R.id.action_hide_completed_tasks -> {
                item.isChecked = !item.isChecked
                viewModel.hideCompletedTasks.value = item.isChecked
                true
            }
            R.id.action_delete_completed_tasks -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}