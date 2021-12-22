package com.nahlasamir244.taskhive.ui.task.addEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nahlasamir244.taskhive.R
import com.nahlasamir244.taskhive.ui.task.tasks.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddEditTaskFragment()
    }

    //delegate property to be injected by dagger
    private val viewModel: TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}