package com.planetoto.customer_component.foundation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object PlanetDimens {
    data class Padding(
        val xSmall: Dp = 8.dp,
        val small: Dp = 16.dp,
        val medium: Dp = 20.dp,
        val large: Dp = 32.dp
    )

    data class BorderRadius(
        val full: Dp = 100.dp,
        val small: Dp = 8.dp,
        val medium: Dp = 10.dp,
        val large: Dp = 20.dp
    )
}

val LocalPadding = compositionLocalOf { PlanetDimens.Padding() }
val LocalBorderRadius = compositionLocalOf { PlanetDimens.BorderRadius() }