package com.nahlasamir244.taskhive.data.netwrok

import com.nahlasamir244.taskhive.data.model.Task
import retrofit2.Response
import javax.inject.Inject

class TaskApiHelperImpl @Inject constructor(private val taskApiService: TaskApiService)
    : TaskApiHelper {
    override suspend fun getTasks(): Response<List<Task>> = taskApiService.getTasks()

}