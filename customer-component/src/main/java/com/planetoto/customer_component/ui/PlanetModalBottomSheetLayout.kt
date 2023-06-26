package com.planetoto.customer_component.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.PlanetColors
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class PlanetModalBottomSheetValue {
    Hidden, Expanded
}

class PlanetModalBottomSheetState(
    initialValue: PlanetModalBottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
) : PlanetSwipeableState<PlanetModalBottomSheetValue>(
    initialValue = initialValue,
    animationSpec = animationSpec,
) {
    /**
     * Whether the bottom sheet is visible.
     */
    val isVisible: Boolean
        get() = currentValue != PlanetModalBottomSheetValue.Hidden

    /**
     * Show the bottom sheet with animation and suspend until it's shown.
     *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun show() {
        animateTo(targetValue = PlanetModalBottomSheetValue.Expanded)
    }

    /**
     * Hide the bottom sheet with animation and suspend until it if fully hidden or animation has
     * been cancelled.
     *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun hide() {
        animateTo(targetValue = PlanetModalBottomSheetValue.Hidden)
    }

    internal val nestedScrollConnection = this.PreUpPostDownNestedScrollConnection

    companion object {
        /**
         * The default [Saver] implementation for [PlanetModalBottomSheetState].
         */
        fun Saver(animationSpec: AnimationSpec<Float>): Saver<PlanetModalBottomSheetState, *> =
            Saver(
                save = { it.currentValue },
                restore = {
                    PlanetModalBottomSheetState(
                        initialValue = it,
                        animationSpec = animationSpec
                    )
                }
            )
    }
}

/**
 * Create a [PlanetModalBottomSheetState] and [remember] it.
 *
 * @param initialValue The initial value of the state.
 * @param animationSpec The default animation that will be used to animate to a new state.
 */
@Composable
fun rememberPlanetModalBottomSheetState(
    initialValue: PlanetModalBottomSheetValue = PlanetModalBottomSheetValue.Hidden,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
): PlanetModalBottomSheetState {
    return rememberSaveable(
        initialValue,
        animationSpec,
        saver = PlanetModalBottomSheetState.Saver(animationSpec = animationSpec)
    ) {
        PlanetModalBottomSheetState(
            initialValue = initialValue,
            animationSpec = animationSpec
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun PlanetModalBottomSheetLayout(
    sheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: PlanetModalBottomSheetState = rememberPlanetModalBottomSheetState(),
    sheetShape: Shape = MaterialTheme.shapes.large,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    showHandlebar: Boolean = true,
    tapOutsideToDismiss: Boolean = true,
    scrimColor: Color = PlanetColors.Solid.black.alpha(0.8f).color,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scrollModifier = if (showHandlebar) {
        Modifier.nestedScroll(sheetState.nestedScrollConnection)
    } else Modifier

    BoxWithConstraints(modifier) {
        val fullHeight = constraints.maxHeight.toFloat()
        val sheetHeightState = remember { mutableStateOf<Float?>(null) }
        Box(Modifier.fillMaxSize()) {
            content()
            Scrim(
                color = scrimColor,
                onDismiss = {
                    val canHide = sheetState.confirmStateChange(PlanetModalBottomSheetValue.Hidden)
                    if (canHide && tapOutsideToDismiss) {
                        scope.launch { sheetState.hide() }
                    }
                },
                visible = sheetState.targetValue != PlanetModalBottomSheetValue.Hidden
            )
        }
        Surface(
            Modifier
                .fillMaxWidth()
                .then(scrollModifier)
                .offset {
                    val y = if (sheetState.anchors.isEmpty()) {
                        // if we don't know our anchors yet, render the sheet as hidden
                        fullHeight.roundToInt()
                    } else {
                        // if we do know our anchors, respect them
                        sheetState.offset.value.roundToInt()
                    }
                    IntOffset(0, y)
                }
                .bottomSheetSwipeable(sheetState, fullHeight, sheetHeightState, showHandlebar)
                .onGloballyPositioned {
                    sheetHeightState.value = it.size.height.toFloat()
                }
                .semantics {
                    if (sheetState.isVisible) {
                        dismiss {
                            if (sheetState.confirmStateChange(PlanetModalBottomSheetValue.Hidden)) {
                                scope.launch { sheetState.hide() }
                            }
                            true
                        }
                    }
                },
            shape = sheetShape,
            elevation = sheetElevation,
            color = sheetBackgroundColor,
            contentColor = contentColorFor(sheetBackgroundColor)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (showHandlebar) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .size(24.dp, 2.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(PlanetColors.Solid.monochrome600.color)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                sheetContent()
            }
        }
    }
}

@ExperimentalMaterialApi
private fun Modifier.bottomSheetSwipeable(
    sheetState: PlanetModalBottomSheetState,
    fullHeight: Float,
    sheetHeightState: State<Float?>,
    isSheetDraggable: Boolean
): Modifier {
    val sheetHeight = sheetHeightState.value
    val modifier = if (sheetHeight != null) {
        val anchors = mapOf(
            fullHeight to PlanetModalBottomSheetValue.Hidden,
            fullHeight - sheetHeight to PlanetModalBottomSheetValue.Expanded
        )
        Modifier.planetSwipeable(
            state = sheetState,
            anchors = anchors,
            orientation = Orientation.Vertical,
            enabled = sheetState.currentValue != PlanetModalBottomSheetValue.Hidden && isSheetDraggable,
            resistance = null
        )
    } else {
        Modifier
    }

    return this.then(modifier)
}

@Composable
private fun Scrim(
    color: Color,
    onDismiss: () -> Unit,
    visible: Boolean
) {
    if (color.isSpecified) {
        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = TweenSpec()
        )
        val dismissModifier = if (visible) {
            Modifier
                .pointerInput(onDismiss) { detectTapGestures { onDismiss() } }
                .semantics(mergeDescendants = true) {
                    onClick {
                        onDismiss()
                        true
                    }
                }
        } else {
            Modifier
        }

        Canvas(
            Modifier
                .fillMaxSize()
                .then(dismissModifier)
        ) {
            drawRect(color = color, alpha = alpha)
        }
    }
}