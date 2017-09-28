package com.example.todo.presentation.feature.tasklist


import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import com.example.todo.R
import com.example.todo.presentation.model.Task
import android.view.LayoutInflater
import com.example.todo.databinding.ItemTaskBinding


class TaskListAdapter(val tasks: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        holder?.bindingItem?.task = tasks[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder{
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding.root)
    }

    override fun getItemCount(): Int = tasks.size


    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bindingItem: ItemTaskBinding = DataBindingUtil.bind(itemView)
    }

    @BindingAdapter("bind:priority")
    fun applyFilter(constraintLayout: ConstraintLayout, priority: Int) {

        val red: Int = constraintLayout.context.resources.getColor(R.color.materialRed)
        val orange: Int = constraintLayout.context.resources.getColor(R.color.materialOrange)
        val yellow: Int = constraintLayout.context.resources.getColor(R.color.materialYellow)

        when (priority) {
            0 -> constraintLayout.setBackgroundColor(red)
            1 -> constraintLayout.setBackgroundColor(orange)
            2 -> constraintLayout.setBackgroundColor(yellow)
        }
    }
}