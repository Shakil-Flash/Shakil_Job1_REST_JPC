package com.flash.shakil_job1_restjpc.utils

import java.sql.Date

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null

) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String): Resource<T>(message = message)
    class Loading<T> : Resource<T>()

}