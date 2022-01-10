package com.nahlasamir244.taskhive.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import com.nahlasamir244.taskhive.utils.Constants
import com.nahlasamir244.taskhive.utils.SortType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskPreferencesManager @Inject constructor(@ApplicationContext context: Context) {
    private val TAG = "TaskPreferencesManager"
    //Data store : does its ops in background thread using flow
    private val taskDataStore = context.createDataStore(Constants.TASK_DATASTORE_NAME)
    val taskPreferencesFlow = taskDataStore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
                Log.e(TAG, "Error reading preferences ",exception )
            }
            else {
                throw exception
            }
        }
        .map {
        val sortType = SortType.valueOf(
            it[Constants.SORT_TYPE_KEY] ?: SortType.BY_DATE.name
        )
        val hideCompleted = it[Constants.HIDE_COMPLETED_KEY] ?: false
        FilterPreferences(sortType,hideCompleted)
    }

    suspend fun updateSortTypePreferences(sortType: SortType){
        taskDataStore.edit {
            it[Constants.SORT_TYPE_KEY] = sortType.name
        }
    }

    suspend fun updateHideCompletedPreferences(hideCompleted:Boolean){
        taskDataStore.edit {
            it[Constants.HIDE_COMPLETED_KEY] = hideCompleted
        }
    }

}}
