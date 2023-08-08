package com.planetoto.customer_component.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.PlanetColors

@Stable
class PlanetScaffoldState(
    val mainScaffoldState: ScaffoldState,
    val sideDialogState: PlanetSideDialogState?,
    val modalBottomSheetState: PlanetModalBottomSheetState?
) {
    constructor(
        scaffoldState: ScaffoldState,
        sideDialogState: PlanetSideDialogState?
    ) : this(scaffoldState, sideDialogState, null)

    constructor(
        scaffoldState: ScaffoldState,
        modalBottomSheetState: PlanetModalBottomSheetState?
    ) : this(scaffoldState, null, modalBottomSheetState)

    val drawerState: DrawerState
        get() = mainScaffoldState.drawerState

    val snackbarHostState: SnackbarHostState
        get() = mainScaffoldState.snackbarHostState

    val isSideDialogOpened: Boolean
        get() = sideDialogState?.isOpen == true

    val isSideDialogClosed: Boolean
        get() = sideDialogState?.isClosed == true

    val isModalBottomSheetVisible: Boolean
        get() = modalBottomSheetState?.isVisible == true

    suspend fun openSideDialog() {
        checkNotNull(sideDialogState)
        sideDialogState.open()
    }

    suspend fun closeSideDialog() {
        checkNotNull(sideDialogState)
        sideDialogState.close()
    }

    suspend fun showModalBottomSheet() {
        checkNotNull(modalBottomSheetState)
        modalBottomSheetState.show()
    }

    suspend fun hideModalBottomSheet() {
        checkNotNull(modalBottomSheetState)
        modalBottomSheetState.hide()
    }
}

@Composable
fun rememberPlanetScaffoldState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    sideDialogState: PlanetSideDialogState
): PlanetScaffoldState = remember {
    PlanetScaffoldState(scaffoldState, sideDialogState)
}

@Composable
fun rememberPlanetScaffoldState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    modalBottomSheetState: PlanetModalBottomSheetState
): PlanetScaffoldState = remember {
    PlanetScaffoldState(scaffoldState, modalBottomSheetState)
}

@Composable
fun rememberPlanetScaffoldState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    modalBottomSheetState: PlanetModalBottomSheetState,
    sideDialogState: PlanetSideDialogState
): PlanetScaffoldState = remember {
    PlanetScaffoldState(scaffoldState, sideDialogState, modalBottomSheetState)
}

/**
 * Simple PlanetScaffold just like Material's Scaffold with a few tweak
 */
@Composable
fun PlanetScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = Color.White,
    backgroundImage: (@Composable () -> Unit)? = null,
    contentColor: Color = contentColorFor(backgroundColor),
    statusBarColor: Color = PlanetColors.Solid.neutralWhite.color,
    content: @Composable (PaddingValues) -> Unit
) {
    backgroundImage?.let {
        Box(modifier = Modifier.fillMaxSize()) {
            it()
            Scaffold(
                modifier = modifier,
                scaffoldState = scaffoldState,
                topBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = statusBarColor)
                            .statusBarsPadding()
                    ) {
                        topBar()
                    }
                },
                bottomBar = {
                    Box(modifier = Modifier.navigationBarsPadding()) {
                        bottomBar()
                    }
                },
                snackbarHost = snackbarHost,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                isFloatingActionButtonDocked = isFloatingActionButtonDocked,
                drawerContent = drawerContent,
                drawerGesturesEnabled = drawerGesturesEnabled,
                drawerShape = drawerShape,
                drawerElevation = drawerElevation,
                drawerBackgroundColor = drawerBackgroundColor,
                drawerContentColor = drawerContentColor,
                drawerScrimColor = drawerScrimColor,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                content = content
            )
        }
    } ?: run {
        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = statusBarColor)
                        .statusBarsPadding()
                ) {
                    topBar()
                }
            },
            bottomBar = {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    bottomBar()
                }
            },
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            isFloatingActionButtonDocked = isFloatingActionButtonDocked,
            drawerContent = drawerContent,
            drawerGesturesEnabled = drawerGesturesEnabled,
            drawerShape = drawerShape,
            drawerElevation = drawerElevation,
            drawerBackgroundColor = drawerBackgroundColor,
            drawerContentColor = drawerContentColor,
            drawerScrimColor = drawerScrimColor,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            content = content
        )
    }
}

/**
 * PlanetScaffold with side dialog support for tablet
 * @param addImePadding add IME padding on base PlanetScaffold
 * instead of PlanetSideDialog (outer composable)
 */
@ExperimentalMaterialApi
@Composable
fun PlanetScaffold(
    modifier: Modifier = Modifier,
    planetScaffoldState: PlanetScaffoldState = rememberPlanetScaffoldState(
        sideDialogState = rememberPlanetSideDialogState(initialValue = DrawerValue.Closed)
    ),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    sideDialogContent: @Composable ColumnScope.() -> Unit,
    sideDialogWidth: Dp? = null,
    sideDialogBackgroundColor: Color = MaterialTheme.colors.surface,
    backgroundColor: Color = Color.White,
    backgroundImage: (@Composable () -> Unit)? = null,
    contentColor: Color = contentColorFor(backgroundColor),
    statusBarColor: Color = PlanetColors.Solid.neutralWhite.color,
    addImePadding: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    requireNotNull(planetScaffoldState.sideDialogState) {
        "PlanetScaffoldState.PlanetSideDialogState can not be null!"
    }

    val scaffoldModifier = remember(addImePadding) {
        if (addImePadding) Modifier.imePadding() else Modifier
    }

    PlanetSideDialog(
        modifier = modifier,
        sideDialogState = planetScaffoldState.sideDialogState,
        gesturesEnabled = planetScaffoldState.sideDialogState.isOpen,
        sideDialogContent = sideDialogContent,
        sideDialogBackgroundColor = sideDialogBackgroundColor,
        drawerWidth = sideDialogWidth,
        content = {
            PlanetScaffold(
                modifier = scaffoldModifier,
                scaffoldState = planetScaffoldState.mainScaffoldState,
                snackbarHost = snackbarHost,
                topBar = topBar,
                bottomBar = bottomBar,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                isFloatingActionButtonDocked = isFloatingActionButtonDocked,
                drawerContent = drawerContent,
                drawerGesturesEnabled = drawerGesturesEnabled,
                drawerShape = drawerShape,
                drawerElevation = drawerElevation,
                drawerBackgroundColor = drawerBackgroundColor,
                drawerContentColor = drawerContentColor,
                drawerScrimColor = drawerScrimColor,
                backgroundColor = backgroundColor,
                backgroundImage = backgroundImage,
                contentColor = contentColor,
                statusBarColor = statusBarColor,
                content = content
            )
        }
    )
}

/**
 * PlanetScaffold with modal bottom sheet support
 * @param addImePadding add IME padding on base PlanetScaffold
 * instead of PlanetModalBottomSheetLayout (outer composable)
 */
@ExperimentalMaterialApi
@Composable
fun PlanetScaffold(
    modifier: Modifier = Modifier,
    planetScaffoldState: PlanetScaffoldState = rememberPlanetScaffoldState(
        modalBottomSheetState = rememberPlanetModalBottomSheetState(initialValue = PlanetModalBottomSheetValue.Hidden)
    ),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    sheetContent: @Composable () -> Unit,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetShape: Shape = MaterialTheme.shapes.large,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    isSheetDraggable: Boolean = true,
    tapOutsideSheetToDismiss: Boolean = true,
    backgroundColor: Color = Color.White,
    backgroundImage: (@Composable () -> Unit)? = null,
    contentColor: Color = contentColorFor(backgroundColor),
    statusBarColor: Color = PlanetColors.Solid.neutralWhite.color,
    addImePadding: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    requireNotNull(planetScaffoldState.modalBottomSheetState) {
        "PlanetScaffoldState.ModalBottomSheetState can not be null!"
    }

    val scaffoldModifier = remember(addImePadding) {
        if (addImePadding) Modifier.imePadding() else Modifier
    }

    PlanetModalBottomSheetLayout(
        modifier = modifier,
        sheetContent = sheetContent,
        sheetState = planetScaffoldState.modalBottomSheetState,
        sheetShape = sheetShape,
        sheetElevation = sheetElevation,
        sheetBackgroundColor = sheetBackgroundColor,
        showHandlebar = isSheetDraggable,
        tapOutsideToDismiss = tapOutsideSheetToDismiss,
        content = {
            PlanetScaffold(
                modifier = scaffoldModifier,
                scaffoldState = planetScaffoldState.mainScaffoldState,
                snackbarHost = snackbarHost,
                topBar = topBar,
                bottomBar = bottomBar,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                isFloatingActionButtonDocked = isFloatingActionButtonDocked,
                drawerContent = drawerContent,
                drawerGesturesEnabled = drawerGesturesEnabled,
                drawerShape = drawerShape,
                drawerElevation = drawerElevation,
                drawerBackgroundColor = drawerBackgroundColor,
                drawerContentColor = drawerContentColor,
                drawerScrimColor = drawerScrimColor,
                backgroundColor = backgroundColor,
                backgroundImage = backgroundImage,
                contentColor = contentColor,
                statusBarColor = statusBarColor,
                content = content
            )
        }
    )
}

/**
 * PlanetScaffold with both side dialog and bottom sheet support
 * @param addImePadding add IME padding on base PlanetScaffold
 * instead of PlanetModalBottomSheetLayout (outer composable)
 */
@ExperimentalMaterialApi
@Composable
fun PlanetScaffold(
    modifier: Modifier = Modifier,
    planetScaffoldState: PlanetScaffoldState = rememberPlanetScaffoldState(
        sideDialogState = rememberPlanetSideDialogState(initialValue = DrawerValue.Closed),
        modalBottomSheetState = rememberPlanetModalBottomSheetState(initialValue = PlanetModalBottomSheetValue.Hidden)
    ),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    sideDialogContent: @Composable ColumnScope.() -> Unit,
    sideDialogWidth: Dp? = null,
    sideDialogBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContent: @Composable () -> Unit,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetShape: Shape = MaterialTheme.shapes.large,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    isSheetDraggable: Boolean = true,
    tapOutsideSheetToDismiss: Boolean = true,
    backgroundColor: Color = Color.White,
    contentColor: Color = contentColorFor(backgroundColor),
    backgroundImage: (@Composable () -> Unit)? = null,
    statusBarColor: Color = PlanetColors.Solid.neutralWhite.color,
    addImePadding: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    requireNotNull(planetScaffoldState.modalBottomSheetState) {
        "PlanetScaffoldState.ModalBottomSheetState can not be null!"
    }

    requireNotNull(planetScaffoldState.sideDialogState) {
        "PlanetScaffoldState.PlanetSideDialogState can not be null!"
    }

    val scaffoldModifier = remember(addImePadding) {
        if (addImePadding) Modifier.imePadding() else Modifier
    }

    PlanetSideDialog(
        modifier = modifier,
        sideDialogState = planetScaffoldState.sideDialogState,
        gesturesEnabled = planetScaffoldState.sideDialogState.isOpen,
        sideDialogContent = sideDialogContent,
        sideDialogBackgroundColor = sideDialogBackgroundColor,
        drawerWidth = sideDialogWidth,
        content = {
            PlanetModalBottomSheetLayout(
                sheetContent = sheetContent,
                sheetState = planetScaffoldState.modalBottomSheetState,
                sheetShape = sheetShape,
                sheetElevation = sheetElevation,
                sheetBackgroundColor = sheetBackgroundColor,
                showHandlebar = isSheetDraggable,
                tapOutsideToDismiss = tapOutsideSheetToDismiss,
                content = {
                    PlanetScaffold(
                        modifier = scaffoldModifier,
                        scaffoldState = planetScaffoldState.mainScaffoldState,
                        snackbarHost = snackbarHost,
                        topBar = topBar,
                        bottomBar = bottomBar,
                        floatingActionButton = floatingActionButton,
                        floatingActionButtonPosition = floatingActionButtonPosition,
                        isFloatingActionButtonDocked = isFloatingActionButtonDocked,
                        drawerContent = drawerContent,
                        drawerGesturesEnabled = drawerGesturesEnabled,
                        drawerShape = drawerShape,
                        drawerElevation = drawerElevation,
                        drawerBackgroundColor = drawerBackgroundColor,
                        drawerContentColor = drawerContentColor,
                        drawerScrimColor = drawerScrimColor,
                        backgroundColor = backgroundColor,
                        backgroundImage = backgroundImage,
                        contentColor = contentColor,
                        statusBarColor = statusBarColor,
                        content = content
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    PlanetScaffold(bottomBar = {
        PlanetButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            text = "WOI WOI WOIW OI"
        ) {

        }
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PlanetColors.Solid.red05.color)
        )
    }
}