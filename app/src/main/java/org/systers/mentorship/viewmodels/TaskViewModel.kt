package org.systers.mentorship.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.systers.mentorship.MentorshipApplication
import org.systers.mentorship.R
import org.systers.mentorship.models.Task
import org.systers.mentorship.remote.datamanager.TaskDataManager
import org.systers.mentorship.remote.requests.TaskRequest
import org.systers.mentorship.remote.responses.CustomResponse
import org.systers.mentorship.utils.CommonUtils
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

/**
 * This class represents the [ViewModel] used for Tasks Screen
 */
class TasksViewModel: ViewModel() {

    var TAG = TasksViewModel::class.java.simpleName

    lateinit var tasksList: List<Task>

    private val taskDataManager: TaskDataManager = TaskDataManager()
    val successfulGet: MutableLiveData<Boolean> = MutableLiveData()
    val successfulPost: MutableLiveData<Boolean> = MutableLiveData()
    val successfulPut: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var message: String

    /**
     * This function lists all tasks from the mentorship relation
     */
    @SuppressLint("CheckResult")
    fun getTasks(relationId: Int) {
        taskDataManager.getAllTasks(relationId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Task>>() {
                    override fun onNext(taskListResponse: List<Task>) {
                        tasksList = taskListResponse
                        successfulGet.value = true
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is IOException -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_please_check_internet)
                            }
                            is TimeoutException -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_request_timed_out)
                            }
                            is HttpException -> {
                                message = CommonUtils.getErrorResponse(throwable).message.toString()
                            }
                            else -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_something_went_wrong)
                                Log.e(TAG, throwable.localizedMessage)
                            }
                        }
                        successfulGet.value = false
                    }

                    override fun onComplete() {
                    }
                })
    }

    /**
     * This function adds a new task to the task list
     * @param relationId id of the current relation of the user
     * @param task body of the task to be created
     */
    @SuppressLint("CheckResult")
    fun addTask(relationId: Int, task: TaskRequest) {
        taskDataManager.createNewTask(relationId, task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CustomResponse>() {

                    override fun onNext(customResponse: CustomResponse) {
                        message = customResponse.message
                        successfulPost.value = true
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is IOException -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_please_check_internet)
                            }
                            is TimeoutException -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_request_timed_out)
                            }
                            is HttpException -> {
                                message = CommonUtils.getErrorResponse(throwable).message.toString()
                            }
                            else -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_something_went_wrong)
                                Log.e(TAG, throwable.localizedMessage)
                            }
                        }
                        successfulPost.value = false
                    }

                    override fun onComplete() {
                    }

                })
    }

    /**
     * This function updates the task as completed
     * @param relationId id of the current relation of the user
     * @param taskId id of the task that is clicked
     */
    @SuppressLint("CheckResult")
    fun completeTask(relationId: Int, taskId: Int){
        taskDataManager.completeTask(relationId, taskId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<CustomResponse>() {

                    override fun onNext(customResponse: CustomResponse) {
                        message = customResponse.message
                        successfulPut.value = true
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is IOException -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_please_check_internet)
                            }
                            is TimeoutException -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_request_timed_out)
                            }
                            is HttpException -> {
                                message = CommonUtils.getErrorResponse(throwable).message.toString()
                            }
                            else -> {
                                message = MentorshipApplication.getContext()
                                        .getString(R.string.error_something_went_wrong)
                                Log.e(TAG, throwable.localizedMessage)
                            }
                        }
                        successfulPut.value = false
                    }

                    override fun onComplete() {
                    }

                })
    }
}