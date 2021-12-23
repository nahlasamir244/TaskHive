package com.nahlasamir244.taskhive.utils

//General Utils file
//can be used to turn statement to expression by returning the object itself
val <T> T.exhaustive: T
    get() = this