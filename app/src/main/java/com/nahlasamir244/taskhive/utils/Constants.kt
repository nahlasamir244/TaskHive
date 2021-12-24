package com.nahlasamir244.taskhive.utils

import android.app.Activity
import androidx.datastore.preferences.preferencesKey

object Constants {
    const val TASK_TABLE_NAME = "task_table"
    const val DATABASE_NAME = "task_hive_database"
    const val TASK_DATASTORE_NAME = "task_preferences"
    //preferences keys
    val SORT_TYPE_KEY = preferencesKey<String>("sort_type")
    val HIDE_COMPLETED_KEY = preferencesKey<Boolean>("hide_completed")
    const val ADD_TASK_RESULT_OK = Activity.RESULT_FIRST_USER
    const val EDIT_TASK_RESULT_OK = Activity.RESULT_FIRST_USER + 1
    const val ADD_EDIT_TASK_REQUEST_KEY = "add_edit_task_request"
    const val ADD_EDIT_TASK_RESULT_KEY = "add_edit_task"
}