package com.planetoto.customer_component.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.planetoto.customer_component.foundation.PlanetColors

@Composable
fun Modifier.disableRipple(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) = this.clickable(
    enabled = enabled,
    indication = null,
    interactionSource = interactionSource,
    onClick = onClick
)

fun Modifier.background(color: PlanetColors.Solid, shape: Shape = RectangleShape) =
    then(background(color = color.color, shape = shape))