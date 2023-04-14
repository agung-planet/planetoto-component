package com.planetoto.customer_component.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
 * @param isOverScrollMode: lazy column animation when over scrolling (bounced effect)
 */
@ExperimentalFoundationApi
@Composable
fun PlanetLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    itemsToListenState: LazyPagingItems<*>,
    isOverScrollMode: Boolean = false,
    onFirstLoad: @Composable (LazyItemScope.() -> Unit) = {
        Box(
            modifier = Modifier.fillParentMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    },
    onEmptyLoad: @Composable (() -> Unit) = {},
    content: LazyListScope.() -> Unit
) {
    if (itemsToListenState.isLoadedEmpty()) {
        onEmptyLoad()
    } else {
        val overScrollConfiguration = if (isOverScrollMode) OverscrollConfiguration() else null
        CompositionLocalProvider(LocalOverscrollConfiguration provides overScrollConfiguration) {
            LazyColumn(
                modifier,
                state,
                contentPadding,
                reverseLayout,
                verticalArrangement,
                horizontalAlignment,
                flingBehavior
            ) {
                content()

                itemsToListenState.apply {
                    when {
                        loadState.append is LoadState.Loading -> item {
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
                        isFirstLoading() -> item(content = onFirstLoad)
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