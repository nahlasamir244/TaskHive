package com.nahlasamir244.taskhive.ui.tasks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nahlasamir244.taskhive.R
import com.nahlasamir244.taskhive.databinding.FragmentTasksBinding
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}