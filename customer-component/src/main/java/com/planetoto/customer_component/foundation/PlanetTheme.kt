package com.planetoto.customer_component.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun PlanetTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalPadding provides PlanetDimens.Padding(),
        LocalBorderRadius provides PlanetDimens.BorderRadius(),
        content = content
    )
}