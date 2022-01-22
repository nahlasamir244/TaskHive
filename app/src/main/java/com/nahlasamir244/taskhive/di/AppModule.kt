package com.nahlasamir244.taskhive.di

import android.app.Application
import androidx.room.Room
import com.nahlasamir244.taskhive.BuildConfig
import com.nahlasamir244.taskhive.data.netwrok.TaskApiHelper
import com.nahlasamir244.taskhive.data.netwrok.TaskApiHelperImpl
import com.nahlasamir244.taskhive.data.netwrok.TaskApiService
import com.nahlasamir244.taskhive.data.offline.database.TaskHiveDatabase
import com.nahlasamir244.taskhive.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
        Room.databaseBuilder(application, TaskHiveDatabase::class.java,Constants.DATABASE_NAME)
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

    @Provides
    @BaseUrl
    fun provideBaseUrl() = "https://taskhive.free.beeceptor.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        //log request and response only in debugging mode
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TaskApiService = retrofit.create(TaskApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(taskApiHelperImpl: TaskApiHelperImpl): TaskApiHelper = taskApiHelperImpl


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class BaseUrl