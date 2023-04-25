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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.LocalBorderRadius
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography
import com.planetoto.customer_component.utils.capitalizeWords

enum class PlanetButtonSize {
    Medium, Large
}

enum class PlanetButtonType {
    Primary, Secondary
}

@Composable
fun PlanetButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    size: PlanetButtonSize = PlanetButtonSize.Medium,
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
    val height = remember(size) {
        when (size) {
            PlanetButtonSize.Medium -> 36.dp
            PlanetButtonSize.Large -> 50.dp
        }
    }
    val typography = remember(size) {
        when (size) {
            PlanetButtonSize.Medium -> PlanetTypography.LabelSmallButton
            PlanetButtonSize.Large -> PlanetTypography.LabelMediumButton
        }
    }

    when (type) {
        PlanetButtonType.Primary -> {
            val textColor = remember(enabled) {
                if (enabled) PlanetColors.Solid.neutralWhite else PlanetColors.Solid.content03
            }

            Button(
                modifier = modifier
                    .height(height)
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
                    typography = typography,
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
                    .height(height)
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
                    typography = typography,
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
    }
}

@Composable
fun PlanetOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    size: PlanetButtonSize = PlanetButtonSize.Medium,
    iconPainter: Painter? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
    color: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    onClick: () -> Unit
) {
    var hasFocus by remember { mutableStateOf(false) }
    val borderColor by remember {
        derivedStateOf {
            if (hasFocus && enabled) BorderStroke(1.dp, PlanetColors.Solid.red07.color) else null
        }
    }
    val height = remember(size) {
        when (size) {
            PlanetButtonSize.Medium -> 36.dp
            PlanetButtonSize.Large -> 50.dp
        }
    }
    val typography = remember(size) {
        when (size) {
            PlanetButtonSize.Medium -> PlanetTypography.LabelSmallButton
            PlanetButtonSize.Large -> PlanetTypography.LabelMediumButton
        }
    }

    OutlinedButton(
        modifier = modifier
            .height(height)
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
        border = borderColor ?: BorderStroke(1.dp, PlanetColors.Solid.neutralWhite.color),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = color.color,
            disabledContentColor = color.color.copy(.3f)
        )
    ) {
        PlanetText(
            text = text.capitalizeWords(),
            typography = typography,
            color = color
        )
        iconPainter?.let {
            Icon(
                painter = it,
                contentDescription = null,
                modifier = Modifier.padding(start = 10.dp),
                tint = color.color
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