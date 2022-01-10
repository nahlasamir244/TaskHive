package com.nahlasamir244.taskhive.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nahlasamir244.taskhive.utils.Constants
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = Constants.TASK_TABLE_NAME)
@Parcelize
data class Task (
    @Json(name = "name")
    val name:String,
    @Json(name = "important")
    val important:Boolean = false,
    @Json(name = "completed")
    val completed:Boolean = false,
    @Json(name = "dateModified")
    val dateModified: Long = System.currentTimeMillis(),
    @Json(name = "id")
    @PrimaryKey(autoGenerate = true) val id:Int = 0
                ) :Parcelable
{
    val dateModifiedFormatted:String
    get() = DateFormat.getDateTimeInstance().format(dateModified)
}
