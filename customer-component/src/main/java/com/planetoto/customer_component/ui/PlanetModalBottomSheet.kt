/*
 * Copyright 2023 PLANET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.planetoto.customer_component.ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.popup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.planetoto.customer_component.foundation.PlanetColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun Scrim(
    color: PlanetColors.Solid,
    onDismissRequest: () -> Unit,
    enableTouchToDismiss: Boolean,
    visible: Boolean
) {
    if (color.color.isSpecified) {
        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = TweenSpec()
        )
        val dismissSheet = if (enableTouchToDismiss && visible) {
            Modifier
                .pointerInput(onDismissRequest) {
                    detectTapGestures {
                        onDismissRequest()
                    }
                }
                .clearAndSetSemantics {}
        } else {
            Modifier
        }
        Canvas(
            Modifier
                .fillMaxSize()
                .then(dismissSheet)
        ) {
            drawRect(color = color.color, alpha = alpha)
        }
    }
}

@Composable
fun PlanetModalBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: PlanetModalBottomSheetState = rememberPlanetModalBottomSheetState(),
    shape: Shape = PlanetModalBottomSheetDefaults.SheetShape,
    backgroundColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    tonalElevation: Dp = 1.dp,
    scrimColor: PlanetColors.Solid = PlanetModalBottomSheetDefaults.ScrimColor,
    showHandlebar: Boolean = false,
    tapOutsideToDismiss: Boolean = true,
    windowInsets: WindowInsets = PlanetModalBottomSheetDefaults.NonFullScreenWindowInsets,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    val animateToDismiss: () -> Unit = {
        if (sheetState.swipeableState.confirmValueChange(PlanetModalBottomSheetValue.Hidden)) {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismissRequest()
                }
            }
        }
    }
    val settleToDismiss: (velocity: Float) -> Unit = {
        scope.launch { sheetState.settle(it) }.invokeOnCompletion {
            if (!sheetState.isVisible) onDismissRequest()
        }
    }
    val nestedScrollModifier = if (showHandlebar) {
        Modifier.nestedScroll(
            remember(sheetState) {
                ConsumeSwipeWithinBottomSheetBoundsNestedScrollConnection(
                    sheetState = sheetState,
                    orientation = Orientation.Vertical,
                    onFling = settleToDismiss
                )
            }
        )
    } else Modifier

    // Callback that is invoked when the anchors have changed.
    val anchorChangeHandler = remember(sheetState, scope) {
        ModalBottomSheetAnchorChangeHandler(
            state = sheetState,
            animateTo = { target, velocity ->
                scope.launch { sheetState.animateTo(target, velocity = velocity) }
            },
            snapTo = { target ->
                val didSnapImmediately = sheetState.trySnapTo(target)
                if (!didSnapImmediately) {
                    scope.launch { sheetState.snapTo(target) }
                }
            }
        )
    }

    ModalBottomSheetPopup(
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion { onDismissRequest() }
        },
        windowInsets = windowInsets
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val fullHeight = constraints.maxHeight
            val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

            Scrim(
                color = scrimColor,
                onDismissRequest = animateToDismiss,
                visible = sheetState.targetValue != PlanetModalBottomSheetValue.Hidden,
                enableTouchToDismiss = tapOutsideToDismiss
            )
            Surface(
                modifier = modifier
                    .widthIn(max = BottomSheetMaxWidth)
                    .fillMaxWidth()
                    .heightIn(max = maxHeight - statusBarHeight)
                    .align(Alignment.TopCenter)
                    .offset {
                        IntOffset(
                            0,
                            sheetState
                                .requireOffset()
                                .toInt()
                        )
                    }
                    .then(nestedScrollModifier)
                    .modalBottomSheetSwipeable(
                        enabled = showHandlebar,
                        sheetState = sheetState,
                        anchorChangeHandler = anchorChangeHandler,
                        screenHeight = fullHeight.toFloat(),
                        onDragStopped = {
                            settleToDismiss(it)
                        }
                    ),
                shape = shape,
                color = backgroundColor.color,
                contentColor = contentColorFor(backgroundColor = backgroundColor.color),
                tonalElevation = tonalElevation,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (showHandlebar) {
                        val dismissActionLabel = "dismiss"

                        Box(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .semantics(mergeDescendants = true) {
                                    // Provides semantics to interact with the bottomsheet based on its
                                    // current value.
                                    dismiss(dismissActionLabel) {
                                        animateToDismiss()
                                        true
                                    }
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .size(24.dp, 2.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(PlanetColors.Solid.monochrome600.color)
                            )
                        }
                    }
                    content()
                }
            }
        }
    }

    LaunchedEffect(sheetState) {
        sheetState.show()
    }
}

/**
 * Create and [remember] a [SheetState] for [ModalBottomSheet].
 */
@Composable
fun rememberPlanetModalBottomSheetState() = rememberSheetState()

internal fun ModalBottomSheetAnchorChangeHandler(
    state: PlanetModalBottomSheetState,
    animateTo: (target: PlanetModalBottomSheetValue, velocity: Float) -> Unit,
    snapTo: (target: PlanetModalBottomSheetValue) -> Unit,
) =
    AnchorChangeHandler<PlanetModalBottomSheetValue> { previousTarget, previousAnchors, newAnchors ->
        val previousTargetOffset = previousAnchors[previousTarget]
        val newTarget = when (previousTarget) {
            PlanetModalBottomSheetValue.Hidden -> PlanetModalBottomSheetValue.Hidden
            PlanetModalBottomSheetValue.Expanded -> {
                val newTarget = if (newAnchors.containsKey(PlanetModalBottomSheetValue.Expanded)) {
                    PlanetModalBottomSheetValue.Expanded
                } else PlanetModalBottomSheetValue.Hidden
                newTarget
            }
        }
        val newTargetOffset = newAnchors.getValue(newTarget)
        if (newTargetOffset != previousTargetOffset) {
            if (state.swipeableState.isAnimationRunning || previousAnchors.isEmpty()) {
                // Re-target the animation to the new offset if it changed
                animateTo(newTarget, state.swipeableState.lastVelocity)
            } else {
                // Snap to the new offset value of the target if no animation was running
                snapTo(newTarget)
            }
        }
    }

/**
 * Popup specific for modal bottom sheet.
 */
@Composable
internal fun ModalBottomSheetPopup(
    onDismissRequest: () -> Unit,
    windowInsets: WindowInsets,
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    val id = rememberSaveable { UUID.randomUUID() }
    val parentComposition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val modalBottomSheetWindow = remember {
        ModalBottomSheetWindow(
            onDismissRequest = onDismissRequest,
            composeView = view,
            saveId = id
        ).apply {
            setCustomContent(
                parent = parentComposition,
                content = {
                    Box(
                        Modifier
                            .semantics { this.popup() }
                            .windowInsetsPadding(windowInsets)
                            .imePadding()
                    ) {
                        currentContent()
                    }
                }
            )
        }
    }

    DisposableEffect(modalBottomSheetWindow) {
        modalBottomSheetWindow.show()
        onDispose {
            modalBottomSheetWindow.disposeComposition()
            modalBottomSheetWindow.dismiss()
        }
    }
}

private fun Modifier.modalBottomSheetSwipeable(
    enabled: Boolean = false,
    sheetState: PlanetModalBottomSheetState,
    anchorChangeHandler: AnchorChangeHandler<PlanetModalBottomSheetValue>,
    screenHeight: Float,
    onDragStopped: CoroutineScope.(velocity: Float) -> Unit,
) = draggable(
    state = sheetState.swipeableState.swipeDraggableState,
    orientation = Orientation.Vertical,
    enabled = sheetState.isVisible && enabled,
    startDragImmediately = sheetState.swipeableState.isAnimationRunning,
    onDragStopped = onDragStopped
).planetSwipeAnchors(
    state = sheetState.swipeableState,
    possibleValues = setOf(
        PlanetModalBottomSheetValue.Hidden,
        PlanetModalBottomSheetValue.Expanded
    ),
    anchorChangeHandler = anchorChangeHandler
) { value, sheetSize ->
    when (value) {
        PlanetModalBottomSheetValue.Hidden -> screenHeight
        PlanetModalBottomSheetValue.Expanded -> if (sheetSize.height != 0) {
            max(0f, screenHeight - sheetSize.height)
        } else null
    }
}

/** Custom compose view for [PlanetModalBottomSheet] */
private class ModalBottomSheetWindow(
    private var onDismissRequest: () -> Unit,
    private val composeView: View,
    saveId: UUID,
) : AbstractComposeView(composeView.context),
    ViewTreeObserver.OnGlobalLayoutListener,
    ViewRootForInspector {
    init {
        id = android.R.id.content
        // Set up view owners
        setViewTreeLifecycleOwner(composeView.findViewTreeLifecycleOwner())
        setViewTreeViewModelStoreOwner(composeView.findViewTreeViewModelStoreOwner())
        setViewTreeSavedStateRegistryOwner(composeView.findViewTreeSavedStateRegistryOwner())
        setTag(androidx.compose.ui.R.id.compose_view_saveable_id_tag, "Popup:$saveId")
        // Enable children to draw their shadow by not clipping them
        clipChildren = false
    }

    private val windowManager =
        composeView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val displayWidth: Int
        get() {
            val density = context.resources.displayMetrics.density
            return (context.resources.configuration.screenWidthDp * density).roundToInt()
        }

    private val params: WindowManager.LayoutParams =
        WindowManager.LayoutParams().apply {
            // Position bottom sheet from the bottom of the screen
            gravity = Gravity.BOTTOM or Gravity.START
            // Application panel window
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            // Fill up the entire app view
            width = displayWidth
            height = WindowManager.LayoutParams.MATCH_PARENT

            // Format of screen pixels
            format = PixelFormat.TRANSLUCENT
            // Title used as fallback for a11y services
            // TODO: Provide bottom sheet window resource
            title = composeView.context.resources.getString(
                androidx.compose.ui.R.string.default_popup_window_title
            )
            // Get the Window token from the parent view
            token = composeView.applicationWindowToken

            // Flags specific to modal bottom sheet.
            flags = flags and (
                    WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES or
                            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                    ).inv()

            flags = flags or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        }

    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    @Composable
    override fun Content() {
        content()
    }

    fun setCustomContent(
        parent: CompositionContext? = null,
        content: @Composable () -> Unit
    ) {
        parent?.let { setParentCompositionContext(it) }
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
    }

    fun show() {
        windowManager.addView(this, params)
    }

    fun dismiss() {
        setViewTreeLifecycleOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        composeView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        windowManager.removeViewImmediate(this)
    }

    /**
     * Taken from PopupWindow. Calls [onDismissRequest] when back button is pressed.
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyDispatcherState == null) {
                return super.dispatchKeyEvent(event)
            }
            if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
                val state = keyDispatcherState
                state?.startTracking(event, this)
                return true
            } else if (event.action == KeyEvent.ACTION_UP) {
                val state = keyDispatcherState
                if (state != null && state.isTracking(event) && !event.isCanceled) {
                    onDismissRequest()
                    return true
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onGlobalLayout() {
        // No-op
    }
}

object PlanetModalBottomSheetDefaults {
    val ScrimColor = PlanetColors.Solid.black.alpha(0.8f)

    val SheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)

    val NonFullScreenWindowInsets
        @Composable
        get() = WindowInsets.systemBars.only(WindowInsetsSides.Vertical)

    val FullScreenWindowInsets
        @Composable
        get() = WindowInsets(top = 0, bottom = 0)
}