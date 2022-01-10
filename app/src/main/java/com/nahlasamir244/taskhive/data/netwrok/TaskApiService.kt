package com.nahlasamir244.taskhive.data.netwrok

import com.nahlasamir244.taskhive.data.model.Task
import retrofit2.Response
import retrofit2.http.GET

interface TaskApiService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>
}