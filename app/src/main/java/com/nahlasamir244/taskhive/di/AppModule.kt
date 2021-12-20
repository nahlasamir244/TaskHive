package com.nahlasamir244.taskhive.di

import android.app.Application
import androidx.room.Room
import com.nahlasamir244.taskhive.data.offline.database.TaskHiveDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

//module is class where we give dagger instructions of how to create dependencies
@Module
@InstallIn(ApplicationComponent::class) // here we add dependencies that we are using inside the whole application
//application scope
object AppModule {
    //we use @provides instead of @inject because we dont own the room class
    @Provides
    @Singleton //singleton scoped (here it's app scoped)
    fun provideTaskDatabase(application: Application, taskCallBack: TaskHiveDatabase.TaskCallBack) =
        //database schema update will case to drop table and create a new one
        Room.databaseBuilder(application, TaskHiveDatabase::class.java,"task_hive_database")
            .fallbackToDestructiveMigration().addCallback(taskCallBack).build() // or add migration strategy
    @Provides
    fun provideTaskDAO(taskHiveDatabase: TaskHiveDatabase) = taskHiveDatabase.taskDao()

    //create coroutine scope that lives the whole app to be easy to control and test better than the global scope

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
    //coroutines get canceled wherever any of its children fails so the supervisorjob stops this behavior to keep the other running
    //whenever a coroutine fails

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope