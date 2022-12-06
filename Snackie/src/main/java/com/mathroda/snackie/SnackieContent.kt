package com.mathroda.snackie

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
fun SnackieCustom(
    modifier: Modifier = Modifier,
    state: SnackieState,
    position: SnackiePosition = SnackiePosition.Bottom,
    duration: Long = 3000L,
    icon: ImageVector = Icons.Default.Star,
    containerColor: Color = Color.Gray,
    contentColor: Color = TextWhite,
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
            containerColor,
            contentColor,
            verticalPadding,
            horizontalPadding,
            icon,
            enterAnimation,
            exitAnimation
        )
    }
}

@Composable
fun SnackieError(
    modifier: Modifier = Modifier,
    state: SnackieState,
    position: SnackiePosition = SnackiePosition.Bottom,
    duration: Long = 3000L,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SnackieComponent(
            state = state,
            duration = duration,
            position = position,
            containerColor = BrightRed,
            contentColor = TextWhite,
            verticalPadding = 12.dp,
            horizontalPadding = 12.dp,
            icon = Icons.Default.Warning,
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = when(position) {
                    is SnackiePosition.Top -> Alignment.Top
                    is SnackiePosition.Bottom -> Alignment.Bottom
                    is SnackiePosition.Float -> Alignment.CenterVertically
                }
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards =  when(position) {
                    is SnackiePosition.Top -> Alignment.Top
                    is SnackiePosition.Bottom -> Alignment.Bottom
                    is SnackiePosition.Float -> Alignment.CenterVertically
                }
            )
        )
    }
}

@Composable
fun SnackieSuccess(
    modifier: Modifier = Modifier,
    state: SnackieState,
    position: SnackiePosition = SnackiePosition.Top,
    duration: Long = 3000L,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SnackieComponent(
            state = state,
            duration = duration,
            position = position,
            containerColor = BrightGreen,
            contentColor = TextWhite,
            verticalPadding = 12.dp,
            horizontalPadding = 12.dp,
            icon = Icons.Default.Check,
            enterAnimation = expandVertically(
                animationSpec = tween(delayMillis = 300),
                expandFrom = when(position) {
                    is SnackiePosition.Top -> Alignment.Top
                    is SnackiePosition.Bottom -> Alignment.Bottom
                    is SnackiePosition.Float -> Alignment.CenterVertically
                }
            ),
            exitAnimation = shrinkVertically(
                animationSpec = tween(delayMillis = 300),
                shrinkTowards =  when(position) {
                    is SnackiePosition.Top -> Alignment.Top
                    is SnackiePosition.Bottom -> Alignment.Bottom
                    is SnackiePosition.Float -> Alignment.CenterVertically
                }
            )
        )
    }
}


@Composable
internal fun SnackieComponent(
    state: SnackieState,
    duration: Long,
    position: SnackiePosition,
    containerColor: Color,
    contentColor: Color,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    icon: ImageVector,
    enterAnimation: EnterTransition,
    exitAnimation: ExitTransition,
) {
    var showSnackie by remember { mutableStateOf(false) }
    val message by rememberUpdatedState(newValue = state.message.value)

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
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = when(position) {
                    is SnackiePosition.Top -> 0.dp
                    is SnackiePosition.Bottom -> 0.dp
                    is SnackiePosition.Float -> 24.dp
                }
            ),
        verticalArrangement = when(position) {
            is SnackiePosition.Top -> Arrangement.Top
            is SnackiePosition.Bottom -> Arrangement.Bottom
            is SnackiePosition.Float -> Arrangement.Bottom
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = state.isNotEmpty() && showSnackie,
            enter = when(position) {
                is SnackiePosition.Top -> enterAnimation
                is SnackiePosition.Bottom -> enterAnimation
                is SnackiePosition.Float -> fadeIn()
            },
            exit = when(position) {
                is SnackiePosition.Top -> exitAnimation
                is SnackiePosition.Bottom -> exitAnimation
                is SnackiePosition.Float -> fadeOut()
            }
        ) {
            Snackie(
                message,
                position,
                containerColor,
                contentColor,
                verticalPadding,
                horizontalPadding,
                icon
            )
        }
    }
}


@Composable
internal fun Snackie(
    message: String?,
    position: SnackiePosition,
    containerColor: Color,
    contentColor: Color,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(
                fraction = when(position) {
                    is SnackiePosition.Top -> 1f
                    is SnackiePosition.Bottom -> 1f
                    is SnackiePosition.Float -> 0.8f
                }
            )
            .background(
                color = containerColor,
                shape = when(position) {
                    is SnackiePosition.Top -> RectangleShape
                    is SnackiePosition.Bottom -> RectangleShape
                    is SnackiePosition.Float -> RoundedCornerShape(8.dp)
                }
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
                text = message ?: "Unknown",
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