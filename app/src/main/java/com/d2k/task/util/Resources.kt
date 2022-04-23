package com.d2k.task.util

data class Resources<out T>(val status: Status, val data: T?, val error: String?) {
    companion object {
        fun <T> success(data: T): Resources<T> = Resources(status = Status.SUCCESS, data = data, error = null)

        fun <T> error(data: T?, message: String): Resources<T> = Resources(status = Status.ERROR, data = data, error = message)

        fun <T> loading(data: T?): Resources<T> = Resources(status = Status.LOADING, data = data, error = null)
    }
}
