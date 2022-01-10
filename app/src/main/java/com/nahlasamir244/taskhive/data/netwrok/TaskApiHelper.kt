package com.nahlasamir244.taskhive.data.netwrok

import com.nahlasamir244.taskhive.data.model.Task
import retrofit2.Response

interface TaskApiHelper {
    suspend fun getTasks(): Response<List<Task>>
}