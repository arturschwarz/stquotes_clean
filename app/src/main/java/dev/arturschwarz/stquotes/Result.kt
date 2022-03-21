package dev.arturschwarz.stquotes

sealed class Result<out T> {
    abstract val data: T?

    data class Pending<out T>(override val data: T?) : Result<T>()

    data class Success<out T>(override val data: T) : Result<T>()

    data class Error<out T>(
        val code: Int? = 0,
        val message: String? = null,
        val exception: Throwable? = null,
        override val data: T? = null
    ) : Result<T>()
}