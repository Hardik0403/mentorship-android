package org.systers.mentorship.remote.datamanager

import io.reactivex.Observable
import org.systers.mentorship.models.Task
import org.systers.mentorship.remote.ApiManager
import org.systers.mentorship.remote.requests.Task_add
import org.systers.mentorship.remote.responses.CustomResponse

/**
 * This class represents the data manager related to Mentorship Task API
 */
class TaskDataManager {

    private val apiManager = ApiManager.instance

    /**
     * This will call a method from Taskservice interface to fetch all tasks
     * @param relationId mentorship relation id
     * @return an Observable of [CustomResponse]
     */
    fun getAllTasks(relationId: Int): Observable<List<Task>> {
        return apiManager.taskService.getAllTasksFromMentorshipRelation(relationId)
    }

    fun addNewTask(relationId: Int,description: Task_add): Observable<CustomResponse> {
        return apiManager.taskService.addTaskToMentorshipRelation(relationId,description)
    }

    fun updateTask(relationId: Int,task: Task_add,taskId:Int): Observable<CustomResponse> {
        return apiManager.taskService.updateTaskCompleted(relationId,task,taskId)
    }

}