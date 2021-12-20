package com.nahlasamir244.taskhive.data.offline.task

import androidx.room.*
import com.nahlasamir244.taskhive.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    //flow is asynchronous stream of data that can be collected inside coroutines
    //on Data change new flow will be generated and can be observed and updates UI
    //can be replaced with livedata
    @Query("SELECT * FROM task_table")
    fun getTasks():Flow<List<Task>>

}