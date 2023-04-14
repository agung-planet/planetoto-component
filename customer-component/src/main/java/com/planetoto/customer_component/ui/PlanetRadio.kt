package com.planetoto.customer_component.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@Composable
fun PlanetRadio(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String,
    isError: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    textColor: PlanetColors.Solid = PlanetColors.Solid.content01
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioIndicator(
            checked = checked,
            onCheckedChange = onCheckedChange,
            isError = isError
        )
        PlanetText(
            text = text,
            color = textColor,
            modifier = Modifier
                .clickable { onCheckedChange(true) }
                .padding(end = 8.dp),
            typography = if (checked) PlanetTypography.BodyDefaultBold else PlanetTypography.BodyDefault14
        )
    }
}

@Composable
private fun RadioIndicator(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isError: Boolean = false
) {
    val background = remember(checked) {
        if (checked) PlanetColors.Solid.blue07 else PlanetColors.Solid.neutralWhite
    }
    val bgAnimation by animateColorAsState(targetValue = background.color)
    val borderColor by remember(checked, isError) {
        derivedStateOf {
            when {
                checked -> PlanetColors.Solid.blue08
                isError -> PlanetColors.Solid.red07
                else -> PlanetColors.Solid.neutralBorder02
            }
        }
    }
    val borderColorAnimation by animateColorAsState(targetValue = borderColor.color)

    Box(
        modifier = modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(bgAnimation)
            .border(1.dp, borderColorAnimation, CircleShape)
            .clickable { onCheckedChange(true) },
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = checked) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(PlanetColors.Solid.neutralWhite.color)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRadio() {
    var checked by remember { mutableStateOf(false) }
    Box {
        PlanetRadio(
            checked = checked,
            onCheckedChange = { checked = it },
            isError = true,
            text = "What a radio"
        )
    }
}