package com.planetoto.customer_component.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PlanetPageIndicatorView(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
    indicatorCount: Int = 5,
    animationDurationInMillis: Int = 500,
    indicatorSize: Dp = 16.dp,
    selectedLength: Dp = indicatorSize,
    indicatorShape: Shape = CircleShape,
    space: Dp = 8.dp,
    activeColor: Color = Color(0xffEC407A),
    inActiveColor: Color = Color.LightGray,
    orientation: IndicatorOrientation = IndicatorOrientation.Horizontal,
    onClick: ((Int) -> Unit)? = null
) {

    val listState = rememberLazyListState()

    val totalWidth: Dp =
        indicatorSize * (indicatorCount - 1) + space * (indicatorCount - 1) + selectedLength
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

    LaunchedEffect(key1 = currentPage) {
        val viewportSize = listState.layoutInfo.viewportSize
        if (orientation == IndicatorOrientation.Horizontal) {
            listState.animateScrollToItem(
                currentPage,
                (widthInPx / 2 - viewportSize.width / 2).toInt()
            )
        } else {
            listState.animateScrollToItem(
                currentPage,
                (widthInPx / 2 - viewportSize.height / 2).toInt()
            )
        }
    }

    if (orientation == IndicatorOrientation.Horizontal) {
        LazyRow(
            modifier = modifier.width(totalWidth),
            state = listState,
            contentPadding = PaddingValues(vertical = space),
            horizontalArrangement = Arrangement.spacedBy(space),
            userScrollEnabled = false
        ) {
            indicatorItems(
                pageCount,
                currentPage,
                indicatorCount,
                animationDurationInMillis,
                indicatorShape,
                activeColor,
                inActiveColor,
                indicatorSize,
                selectedLength,
                onClick
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.height(totalWidth),
            state = listState,
            contentPadding = PaddingValues(horizontal = space),
            verticalArrangement = Arrangement.spacedBy(space),
            userScrollEnabled = false
        ) {
            indicatorItems(
                pageCount,
                currentPage,
                indicatorCount,
                animationDurationInMillis,
                indicatorShape,
                activeColor,
                inActiveColor,
                indicatorSize,
                selectedLength,
                onClick
            )
        }
    }

}

private fun LazyListScope.indicatorItems(
    itemCount: Int,
    currentItem: Int,
    indicatorCount: Int,
    animationDurationInMillis: Int,
    indicatorShape: Shape,
    activeColor: Color,
    inActiveColor: Color,
    indicatorSize: Dp,
    selectedLength: Dp,
    onClick: ((Int) -> Unit)?
) {
    items(itemCount) { index ->

        val isSelected = (index == currentItem)

        // Index of item in center when odd number of indicators are set
        // for 5 indicators this is 2nd indicator place
        val centerItemIndex = indicatorCount / 2

        val right1 =
            (currentItem < centerItemIndex &&
                    index >= indicatorCount - 1)

        val right2 =
            (currentItem >= centerItemIndex &&
                    index >= currentItem + centerItemIndex &&
                    index < itemCount - centerItemIndex + 1)
        val isRightEdgeItem = right1 || right2

        // Check if this item's distance to center item is smaller than half size of
        // the indicator count when current indicator at the center or
        // when we reach the end of list. End of the list only one item is on edge
        // with 10 items and 7 indicators
        // 7-3= 4th item can be the first valid left edge item and
        val isLeftEdgeItem =
            index <= currentItem - centerItemIndex &&
                    currentItem > centerItemIndex &&
                    index < itemCount - indicatorCount + 1

        val width: Dp by animateDpAsState(
            targetValue = if (isSelected) {
                selectedLength
            } else {
                indicatorSize
            },
            animationSpec = tween(
                durationMillis = animationDurationInMillis,
            )
        )

        Box(
            modifier = Modifier
                .graphicsLayer {
                    val scale = if (isSelected) {
                        1f
                    } else if (isLeftEdgeItem || isRightEdgeItem) {
                        1f
                    } else {
                        1f
                    }
                    scaleX = scale
                    scaleY = scale
                }
                .size(width = width, height = indicatorSize)
                .clip(indicatorShape)
                .size(indicatorSize)
                .background(
                    if (isSelected) activeColor else inActiveColor,
                    indicatorShape
                )
                .then(
                    if (onClick != null) {
                        Modifier
                            .clickable {
                                onClick.invoke(index)
                            }
                    } else Modifier
                )
        )
    }
}

enum class IndicatorOrientation {
    Horizontal, Vertical
}