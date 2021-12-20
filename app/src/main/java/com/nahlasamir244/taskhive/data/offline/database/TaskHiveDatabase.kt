package com.nahlasamir244.taskhive.data.offline.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.offline.task.TaskDAO
import com.nahlasamir244.taskhive.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskHiveDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDAO

    //inject : tells dagger how to create instance of this class
    //and makes the dagger passes the dependencies passed to constructor
    class TaskCallBack @Inject constructor (
        private val taskHiveDatabase: Provider<TaskHiveDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
            ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase)  { //called after .build() is finished
            super.onCreate(db)
            //this method is called the first time to open the database only
            //provider means the dependency instantiated lazily when we call , and the oncreate is
            //called after the database is create so this solved the circular dependency to do db ops using dao inside the callback
            val taskDAO = taskHiveDatabase.get().taskDao()
            //coroutines : like lightweight thread that suspend fun can be executed inside
            //coroutines : knows how to suspend execution
            // coroutine scope :how long this coroutines should run and when to be cancelled : global scope runs as long as app is running untill coroutines is finished
           applicationScope.launch {
               taskDAO.insert(Task("call my supervisor", important = true))
               taskDAO.insert(Task("clean the room"))
               taskDAO.insert(Task("prepare dinner"))
               taskDAO.insert(Task("call my friend"))
               taskDAO.insert(Task("do the laundry", completed = true))
               taskDAO.insert(Task("visit the doctor",important = true))
           }

        }
    }
}