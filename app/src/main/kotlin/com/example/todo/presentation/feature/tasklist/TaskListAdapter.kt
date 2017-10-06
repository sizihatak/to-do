package com.example.todo.presentation.feature.tasklist


import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.presentation.model.Task


class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var tasks: MutableList<Task> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        holder?.bindingItem?.task = tasks[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding.root)
    }

    override fun getItemCount(): Int = tasks.size


    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bindingItem: ItemTaskBinding = DataBindingUtil.bind(itemView)
    }

    companion object {
        @JvmStatic
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
}