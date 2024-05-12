package com.ccink.model.model

import java.io.IOException

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error: IOException) : Resource<Nothing>()
}
