package com.planetoto.customer_component.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.PlanetColors

enum class PlanetDividerOrientation {
    Horizontal, Vertical
}

@Composable
fun PlanetDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: PlanetColors.Solid = PlanetColors.Solid.neutralBorder01,
    orientation: PlanetDividerOrientation = PlanetDividerOrientation.Horizontal
) {
    when (orientation) {
        PlanetDividerOrientation.Horizontal -> {
            Divider(modifier = modifier, thickness = thickness, color = color.color)
        }

        PlanetDividerOrientation.Vertical -> {
            Divider(
                modifier = modifier
                    .width(thickness)
                    .fillMaxHeight(),
                color = color.color
            )
        }
    }
}