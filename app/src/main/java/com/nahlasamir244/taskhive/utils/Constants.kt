package com.nahlasamir244.taskhive.utils

import androidx.datastore.preferences.preferencesKey

object Constants {
    const val TASK_TABLE_NAME = "task_table"
    const val DATABASE_NAME = "task_hive_database"
    const val TASK_DATASTORE_NAME = "task_preferences"
    //preferences keys
    val SORT_TYPE_KEY = preferencesKey<String>("sort_type")
    val HIDE_COMPLETED_KEY = preferencesKey<Boolean>("hide_completed")

}