package org.systers.mentorship.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.task_list_item.view.*
import org.systers.mentorship.MentorshipApplication
import org.systers.mentorship.R
import org.systers.mentorship.models.Task

/**
 * This class represents the adapter that fills in each view of the Tasks recyclerView
 * @param tasksList list of tasks taken up by the user
 * @param markTask function to be called when an item from Tasks list is clicked
 */
class TasksAdapter(
        private val tasksList: List<Task>,
        private val markTask: (taskId: Int) -> Unit
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    val context = MentorshipApplication.getContext();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
            TaskViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.task_list_item, parent, false))

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = tasksList[position]
        val itemView = holder.itemView

        itemView.cbTask.text = item.description
        itemView.cbTask.setOnClickListener { markTask(item.id) }
    }

    override fun getItemCount(): Int = tasksList.size

    /**
     * This class holds a view for each item of the Tasks list
     * @param itemView represents each view of Tasks list
     */
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}