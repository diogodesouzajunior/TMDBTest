package com.diogo.tmdbtest.domain.utils

sealed class Results<out T> {

    data class Success<out T>(val value: T) : Results<T>()
    data class Failure(val exception: Throwable) : Results<Nothing>()

    val isSuccess get() = this is Success<T>
    val isFailure get() = this is Failure

    fun getOrNull(): T? = (this as? Success)?.value
    fun exceptionOrNull(): Throwable? = (this as? Failure)?.exception
}