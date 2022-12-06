package com.mathroda.snackie

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*
import kotlin.concurrent.schedule

@Composable
fun rememberSnackieState(): SnackieState {
    return remember { SnackieState() }
}

@Composable
fun SnackieContent(
    modifier: Modifier = Modifier,
    state: SnackieState,
    position: SnackiePosition = SnackiePosition.Top,
    duration: Long = 3000L,
    successIcon: ImageVector = Icons.Default.Check,
    errorIcon: ImageVector = Icons.Default.Warning,
    successContainerColor: Color = BrightGreen,
    successContentColor: Color = TextWhite,
    errorContainerColor: Color = BrightRed,
    errorContentColor: Color = TextWhite,
    enterAnimation: EnterTransition = expandVertically(
        animationSpec = tween(delayMillis = 300),
        expandFrom = when(position) {
            is SnackiePosition.Top -> Alignment.Top
            is SnackiePosition.Bottom -> Alignment.Bottom
            is SnackiePosition.Float -> Alignment.CenterVertically
        }
    ),
    exitAnimation: ExitTransition = shrinkVertically(
        animationSpec = tween(delayMillis = 300),
        shrinkTowards =  when(position) {
            is SnackiePosition.Top -> Alignment.Top
            is SnackiePosition.Bottom -> Alignment.Bottom
            is SnackiePosition.Float -> Alignment.CenterVertically
        }
    ),
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SnackieComponent(
            state,
            duration,
            position,
            successContainerColor,
            errorContainerColor,
            successContentColor,
            errorContentColor,
            verticalPadding,
            horizontalPadding,
            successIcon,
            errorIcon,
            enterAnimation,
            exitAnimation
        )
    }
}


@Composable
internal fun SnackieComponent(
    state: SnackieState,
    duration: Long,
    position: SnackiePosition,
    successContainerColor: Color,
    errorContainerColor: Color,
    successContentColor: Color,
    errorContentColor: Color,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    successIcon: ImageVector,
    errorIcon: ImageVector,
    enterAnimation: EnterTransition,
    exitAnimation: ExitTransition,
) {
    var showSnackie by remember { mutableStateOf(false) }
    val error by rememberUpdatedState(newValue = state.error.value?.message)
    val message by rememberUpdatedState(newValue = state.success.value)

    DisposableEffect(
        key1 = state.updateState
    ) {
        showSnackie = true
        val timer = Timer("Animation Timer", true)
        timer.schedule(duration) {
            showSnackie = false
        }
        onDispose {
            timer.cancel()
            timer.purge()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = when(position) {
            is SnackiePosition.Top -> Arrangement.Top
            is SnackiePosition.Bottom -> Arrangement.Bottom
            is SnackiePosition.Float -> Arrangement.Center
        }
    ) {
        AnimatedVisibility(
            visible = state.isError() && showSnackie || state.isSuccess() && showSnackie,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            Snackie(
                message,
                error,
                successContainerColor,
                errorContainerColor,
                successContentColor,
                errorContentColor,
                verticalPadding,
                horizontalPadding,
                successIcon,
                errorIcon
            )
        }
    }
}


@Composable
internal fun Snackie(
    message: String?,
    error: String?,
    successContainerColor: Color,
    errorContainerColor: Color,
    successContentColor: Color,
    errorContentColor: Color,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    successIcon: ImageVector,
    errorIcon: ImageVector,
) {

    val containerColor =  if (error.isNullOrBlank()) successContainerColor else errorContainerColor
    val contentColor = if (error.isNullOrBlank()) successContentColor else errorContentColor
    val icon = if (error.isNullOrBlank()) successIcon else errorIcon


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                containerColor
            )
            .padding(vertical = verticalPadding)
            .padding(horizontal = horizontalPadding)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Snackie Icon",
                tint = contentColor
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = message ?: (error ?: "Unknown"),
                color = contentColor,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }
    }
}

val BrightGreen = Color(0xFF50DE91)
val BrightRed = Color(0xFFFE4024)
val TextWhite = Color(0xFFEEEEEE)