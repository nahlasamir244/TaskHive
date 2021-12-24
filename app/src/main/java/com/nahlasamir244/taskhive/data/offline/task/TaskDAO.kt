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
    // || is append operator not or
    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed=0) AND name LIKE '%' || :searchKeyWord || '%' ORDER BY important DESC , name")
    fun getTasksSortedByName(searchKeyWord:String,hideCompleted:Boolean):Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed=0) AND name LIKE '%' || :searchKeyWord || '%' ORDER BY important DESC , dateModified")
    fun getTasksSortedByDateCreated(searchKeyWord:String,hideCompleted:Boolean):Flow<List<Task>>

    @Query("DELETE FROM task_table WHERE completed = 1")
    suspend fun deleteCompletedTasks()

}