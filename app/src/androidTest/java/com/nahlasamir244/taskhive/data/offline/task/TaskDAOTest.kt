package com.nahlasamir244.taskhive.data.offline.task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nahlasamir244.taskhive.data.model.Task
import com.nahlasamir244.taskhive.data.offline.database.TaskHiveDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDAOTest {
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()
    private lateinit var database: TaskHiveDatabase
    private lateinit var dao :TaskDAO
    @Before
    fun setUp() {
        //hold db is ram not in persist storage not real db
        //allow main thread queries : db queries are blocking code but we want them to run
        //sequentially in test cases
        database =Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskHiveDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.taskDao()
    }
    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertingTaskTest()= runBlockingTest {
        val task = Task("study unit test", important = true, completed = false)
        dao.insert(task)
        val list:MutableList<Task> = mutableListOf()
            dao.getTasksSortedByName("",false).collectLatest {
                list.addAll(it)
            }
        assert(list.contains(task))
    }

    @Test
    fun deletingTaskTest()= runBlockingTest {
        val task = Task("study unit test", important = true, completed = false)
        dao.insert(task)
        dao.delete(task)
        val list:MutableList<Task> = mutableListOf()
        dao.getTasksSortedByName("",false).collectLatest {
            list.addAll(it)
        }
        assertFalse(list.contains(task))
    }
}