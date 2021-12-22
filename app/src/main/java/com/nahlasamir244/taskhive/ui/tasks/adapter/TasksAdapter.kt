package com.nahlasamir244.taskhive.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.databinding.ItemTaskBinding

//listAdapter is subclass of recyclerview adapter
//listAdapter is useful whenever you have a reactive data source
//each time you get a completely new list use submitList() to dispatch the new list object with changes
// it calculate the difference and dispatch the correct updates and animations
//flowable return new listen not single change event
//listAdapter does its op in background thread
//list adapter needs 1- the type of list object to be bind
//2- viewholder class to bind the data into item layout
//3- DiffUtilItemCallback to be passed to constructor
class TasksAdapter(
    private val tasksAdapterEventHandler: TasksAdapterEventHandler)
    : ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val itemTaskBinding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return TasksViewHolder(itemTaskBinding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class TasksViewHolder(private val itemTaskBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemTaskBinding.root) {
        init {
            itemTaskBinding.apply {
                checkboxTaskCompleted.setOnClickListener{
                    val currentPosition = adapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION){
                        tasksAdapterEventHandler.onTaskItemCompletedChecked(getItem(currentPosition),
                            checkboxTaskCompleted.isChecked)
                    }
                }
                root.setOnClickListener {
                    val currentPosition = adapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION){
                        tasksAdapterEventHandler.onTaskItemClicked(getItem(currentPosition))
                    }
                }
            }
        }
            fun bind(task:Task) {
                itemTaskBinding.apply {
                    checkboxTaskCompleted.isChecked = task.completed
                    textViewTaskName.text = task.name
                    textViewTaskName.paint.isStrikeThruText = task.completed
                    imageViewTaskImportant.isVisible = task.important
                }
            }
    }

    class TasksDiffUtil : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem.id == newItem.id //cause id is unique even if position has changed

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem == newItem // using data class which implements the ==

    }
}