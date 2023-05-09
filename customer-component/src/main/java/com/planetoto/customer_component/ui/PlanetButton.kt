package com.planetoto.customer_component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.LocalBorderRadius
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography
import com.planetoto.customer_component.utils.capitalizeWords

sealed class PlanetButtonSize(val height: Dp, val typography: PlanetTypography) {
    object Medium : PlanetButtonSize(36.dp, PlanetTypography.LabelMediumButton)
    object Large : PlanetButtonSize(50.dp, PlanetTypography.LabelBigButton)
}

sealed interface PlanetButtonType {
    object Primary : PlanetButtonType
    object Secondary : PlanetButtonType
    data class Tertiary(
        val contentColor: PlanetColors.Solid = PlanetColors.Solid.content03,
        val borderColor: PlanetColors.Solid = contentColor,
        val backgroundColor: PlanetColors.Solid = PlanetColors.Solid.content03
    ) : PlanetButtonType
}

@Composable
fun PlanetButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    size: PlanetButtonSize = PlanetButtonSize.Large,
    type: PlanetButtonType = PlanetButtonType.Primary,
    iconPainter: Painter? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
    onClick: () -> Unit
) {
    var hasFocus by remember { mutableStateOf(false) }
    val borderColor by remember {
        derivedStateOf {
            if (hasFocus && enabled) BorderStroke(1.dp, PlanetColors.Solid.red07.color) else null
        }
    }

    when (type) {
        PlanetButtonType.Primary -> {
            val textColor = remember(enabled) {
                if (enabled) PlanetColors.Solid.neutralWhite else PlanetColors.Solid.content03
            }

            Button(
                modifier = modifier
                    .height(size.height)
                    .onFocusChanged {
                        hasFocus = it.hasFocus || it.isFocused
                    },
                onClick = onClick,
                enabled = enabled,
                contentPadding = contentPadding,
                shape = RoundedCornerShape(LocalBorderRadius.current.large),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PlanetColors.Solid.blue06.color,
                    disabledBackgroundColor = PlanetColors.Solid.neutralBorder01.color,
                    contentColor = PlanetColors.Solid.neutralWhite.color,
                    disabledContentColor = PlanetColors.Solid.content03.color
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 4.dp,
                    hoveredElevation = 2.dp,
                    focusedElevation = 2.dp
                ),
                border = borderColor
            ) {
                PlanetText(
                    text = text.capitalizeWords(),
                    typography = size.typography,
                    color = textColor
                )
                iconPainter?.let {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
        PlanetButtonType.Secondary -> {
            val textColor = remember(enabled) {
                if (enabled) PlanetColors.Solid.blue08 else PlanetColors.Solid.content03
            }

            Button(
                modifier = modifier
                    .height(size.height)
                    .onFocusChanged {
                        hasFocus = it.hasFocus || it.isFocused
                    },
                onClick = onClick,
                enabled = enabled,
                contentPadding = contentPadding,
                shape = RoundedCornerShape(LocalBorderRadius.current.large),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PlanetColors.Solid.blue01.color,
                    disabledBackgroundColor = PlanetColors.Solid.neutralBg.color,
                    contentColor = PlanetColors.Solid.blue08.color,
                    disabledContentColor = PlanetColors.Solid.content03.color
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 4.dp,
                    hoveredElevation = 2.dp,
                    focusedElevation = 2.dp
                ),
                border = borderColor
            ) {
                PlanetText(
                    text = text.capitalizeWords(),
                    typography = size.typography,
                    color = textColor
                )
                iconPainter?.let {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
        is PlanetButtonType.Tertiary -> {
            PlanetOutlinedButton(
                modifier = modifier,
                text = text,
                enabled = enabled,
                size = size,
                iconPainter = iconPainter,
                contentPadding = contentPadding,
                contentColor = type.contentColor,
                backgroundColor = type.backgroundColor,
                borderColor = type.borderColor,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun PlanetOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    size: PlanetButtonSize = PlanetButtonSize.Medium,
    iconPainter: Painter? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
    contentColor: PlanetColors.Solid = PlanetColors.Solid.content03,
    borderColor: PlanetColors.Solid = contentColor,
    backgroundColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    onClick: () -> Unit
) {
    var hasFocus by remember { mutableStateOf(false) }
    val borderColorByState by remember {
        derivedStateOf {
            if (hasFocus && enabled) PlanetColors.Solid.red07 else borderColor
        }
    }

    OutlinedButton(
        modifier = modifier
            .height(size.height)
            .onFocusChanged {
                hasFocus = it.hasFocus || it.isFocused
            },
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(LocalBorderRadius.current.large),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp,
            hoveredElevation = 2.dp,
            focusedElevation = 2.dp
        ),
        border = BorderStroke(1.dp, borderColorByState.color),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor.color,
            contentColor = contentColor.color,
            disabledContentColor = contentColor.color.copy(.3f)
        )
    ) {
        PlanetText(
            text = text.capitalizeWords(),
            typography = size.typography,
            color = contentColor
        )
        iconPainter?.let {
            Icon(
                painter = it,
                contentDescription = null,
                modifier = Modifier.padding(start = 10.dp),
                tint = contentColor.color
            )
        }
    }
}

@Preview
@Composable
private fun PreviewButton() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlanetButton(text = "Primary enabled", onClick = {})
            PlanetButton(text = "Primary disabled", enabled = false, onClick = {})
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlanetButton(
                text = "Secondary enabled",
                type = PlanetButtonType.Secondary,
                onClick = {}
            )
            PlanetButton(
                text = "Secondary disabled",
                enabled = false,
                type = PlanetButtonType.Secondary,
                onClick = {})
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlanetOutlinedButton(text = "Tertiary enabled", onClick = {})
            PlanetOutlinedButton(text = "Tertiary disabled", enabled = false, onClick = {})
        }
    }
}