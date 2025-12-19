package network

import kotlinx.coroutines.CancellationException

sealed class NetworkState<out T> {
    abstract val data: T?

    data object AFK : NetworkState<Nothing>() {
        override val data: Nothing? = null
    }

    data class Loading<out T>(override val data: T? = null) : NetworkState<T>()

    data class Success<out T>(override val data: T) : NetworkState<T>()

    data class Error<out T>(
        val error: Throwable,
        val prettyPrint: String,
        val code: Int,
        override val data: T? = null
    ) : NetworkState<T>()


    fun <R> NetworkState<R>.saveState(
        prevData: R?,
        onError: (Error<R>) -> Error<R> = { error -> error },
        onSuccess: (Success<R>) -> Success<R> = { it }
    ): NetworkState<R> = when (this) {
        AFK -> AFK
        is Error<R> -> onError(
            Error<R>(
                error = error,
                prettyPrint = prettyPrint,
                code = code,
                data = prevData
            )
        )

        is Loading<R> -> Loading<R>(prevData)
        is Success<R> -> onSuccess(this)
    }

    fun <R> NetworkState<R>.onCoroutineDeath(
        initialState: NetworkState<R> = Error(
            CancellationException(""),
            "coroutineDeath",
            0
        )
    ) =
        if (isLoading()) AFK else this


    fun isLoading() =
        this is Loading

    fun isAFK() =
        this is AFK

    fun isErrored() =
        this is Error

    inline fun <R> defaultWhen(onSuccess: (Success<T>) -> NetworkState<R>) = when (this) {
        AFK -> AFK
        is Error -> NetworkState.Error<R>(
            error = this.error,
            prettyPrint = this.prettyPrint,
            code = this.code,
            data = null
        )

        is Loading -> Loading<R>(null)
        is Success -> onSuccess(this)
    }

    fun onError(onError: (Error<*>) -> Unit) {
        if (this is Error) onError(this)
    }

    fun handle(onError: (Error<*>) -> Unit = {}, onSuccess: (Success<T>) -> Unit = {}) {
        if (this is Success) {
            onSuccess(this)
        } else if (this is Error) {
            onError(this)
        }
    }

    fun <T> NetworkState<List<T>>.removeItemFromList(
        predicate: (T) -> Boolean
    ): NetworkState<List<T>> {
        return when (this) {
            is Success -> {
                val filteredList = data.filterNot(predicate)
                Success(filteredList)
            }

            is Error -> {
                data?.let { currentList ->
                    val filteredList = currentList.filterNot(predicate)
                    this.copy(data = filteredList)
                } ?: this
            }

            is Loading -> {
                data?.let { currentList ->
                    val filteredList = currentList.filterNot(predicate)
                    this.copy(data = filteredList)
                } ?: this
            }

            AFK -> this
        }
    }

    fun <T> NetworkState<List<T>>.updateItemInList(
        predicate: (T) -> Boolean,
        transform: (T) -> T
    ): NetworkState<List<T>> {

        return when (this) {
            is Success -> {
                val updatedList = data.map { item ->
                    if (predicate(item)) transform(item) else item
                }
                Success(updatedList)
            }

            is Error -> {
                data?.let { currentList ->
                    val updatedList = currentList.map { item ->
                        if (predicate(item)) transform(item) else item
                    }
                    this.copy(data = updatedList)
                } ?: this
            }

            is Loading -> {
                data?.let { currentList ->
                    val updatedList = currentList.map { item ->
                        if (predicate(item)) transform(item) else item
                    }
                    this.copy(data = updatedList)
                } ?: this
            }

            AFK -> this
        }
    }


}