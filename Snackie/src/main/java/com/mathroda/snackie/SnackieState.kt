package com.mathroda.snackie

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SnackieState {

    private val _success = mutableStateOf<String?>(null)
    val success: State<String?> = _success

    private val _error = mutableStateOf<Exception?>(null)
    val error: State<Exception?> = _error

    var updateState by  mutableStateOf(false)
    private set

    fun addSuccess(message: String) {
        _error.value = null
        _success.value = message
        updateState = !updateState
    }

    fun addError(exception: Exception) {
        _error.value = exception
        _success.value = null
        updateState = !updateState
    }

    fun isError(): Boolean {
        return _error.value != null
    }

    fun isSuccess(): Boolean {
        return _success.value != null
    }

}
