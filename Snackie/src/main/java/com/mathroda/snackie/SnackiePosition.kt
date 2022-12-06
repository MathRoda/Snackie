package com.mathroda.snackie

sealed class SnackiePosition {

    object Top: SnackiePosition()

    object Bottom: SnackiePosition()

    object Float: SnackiePosition()
}
