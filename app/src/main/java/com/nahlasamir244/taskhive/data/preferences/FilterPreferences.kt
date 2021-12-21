package com.nahlasamir244.taskhive.data.preferences

import com.nahlasamir244.taskhive.utils.SortType


data class FilterPreferences(var sortType:SortType, var hideCompleted:Boolean ) {
}