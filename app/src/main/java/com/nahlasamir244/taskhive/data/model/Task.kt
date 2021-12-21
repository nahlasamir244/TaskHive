package com.nahlasamir244.taskhive.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nahlasamir244.taskhive.utils.Constants
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = Constants.TASK_TABLE_NAME)
@Parcelize
data class Task (
    val name:String,
    val important:Boolean = false,
    val completed:Boolean = false,
    val dateModified: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id:Int = 0
                ) :Parcelable
{
    val dateModifiedFormatted:String
    get() = DateFormat.getDateTimeInstance().format(dateModified)
}
