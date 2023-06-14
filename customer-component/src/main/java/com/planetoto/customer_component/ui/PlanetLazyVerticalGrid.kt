package com.planetoto.customer_component.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography
import com.planetoto.customer_component.utils.isFirstLoading
import com.planetoto.customer_component.utils.isLoadedEmpty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @param coroutineScope: Coroutine scope for retry if data failed to fetch
 * @param itemsToListenState: Main items to listen the state
 */
@ExperimentalFoundationApi
@Composable
fun PlanetLazyVerticalGrid(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    columns: GridCells,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    itemsToListenState: LazyPagingItems<*>,
    columnSize: Int = 2,
    isOverScrollMode: Boolean = false,
    onFirstLoad: @Composable (LazyGridItemScope.() -> Unit) = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(32.dp),
                color = PlanetColors.Solid.blue07.color,
                strokeWidth = 3.dp
            )
        }
    },
    onEmptyLoad: @Composable (() -> Unit) = {},
    content: LazyGridScope.() -> Unit
) {
    if (itemsToListenState.isLoadedEmpty()) {
        onEmptyLoad()
    } else {
        val overScrollConfiguration = if (isOverScrollMode) OverscrollConfiguration() else null
        CompositionLocalProvider(LocalOverscrollConfiguration provides overScrollConfiguration) {
            LazyVerticalGrid(
                modifier = modifier,
                state = state,
                columns = columns,
                contentPadding = contentPadding,
                reverseLayout = reverseLayout,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement,
                flingBehavior = flingBehavior
            ) {
                content()

                itemsToListenState.apply {
                    when {
                        loadState.append is LoadState.Loading -> item(span = { GridItemSpan(columnSize) }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )

                                PlanetText(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = stringResource(id = R.string.loading),
                                    typography = PlanetTypography.BodyDefault14,
                                    color = PlanetColors.Solid.content02
                                )
                            }
                        }
                        isFirstLoading() -> item(span = { GridItemSpan(columnSize) }) {
                            onFirstLoad()
                        }
                        loadState.append is LoadState.Error || loadState.refresh is LoadState.Error -> {
                            coroutineScope.launch {
                                delay(5000)
                                retry()
                            }
                        }
                    }
                }
            }
        }
    }
}