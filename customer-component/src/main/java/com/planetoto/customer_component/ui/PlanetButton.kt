package com.planetoto.customer_component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
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
    iconPainter: Painter? = null,
    enabled: Boolean = true,
    size: PlanetButtonSize = PlanetButtonSize.Large,
    type: PlanetButtonType = PlanetButtonType.Primary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    val (hasFocus, setHasFocus) = remember { mutableStateOf(false) }

    when (type) {
        PlanetButtonType.Primary -> {
            PrimaryButton(
                modifier = modifier,
                text = text,
                iconPainter = iconPainter,
                hasFocus = hasFocus,
                setHasFocus = setHasFocus,
                enabled = enabled,
                size = size,
                contentPadding = contentPadding,
                onClick = onClick
            )
        }

        PlanetButtonType.Secondary -> {
            SecondaryButton(
                modifier = modifier,
                text = text,
                iconPainter = iconPainter,
                hasFocus = hasFocus,
                setHasFocus = setHasFocus,
                enabled = enabled,
                size = size,
                contentPadding = contentPadding,
                onClick = onClick
            )
        }

        is PlanetButtonType.Tertiary -> {
            TertiaryButton(
                modifier = modifier,
                text = text,
                iconPainter = iconPainter,
                enabled = enabled,
                hasFocus = hasFocus,
                setHasFocus = setHasFocus,
                size = size,
                contentPadding = contentPadding,
                contentColor = type.contentColor,
                borderColor = type.borderColor,
                backgroundColor = type.backgroundColor,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    iconPainter: Painter? = null,
    hasFocus: Boolean,
    setHasFocus: (Boolean) -> Unit,
    enabled: Boolean = true,
    size: PlanetButtonSize = PlanetButtonSize.Large,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    val textColor = remember(enabled) {
        if (enabled) PlanetColors.Solid.neutralWhite else PlanetColors.Solid.content03
    }

    val borderColor by remember {
        derivedStateOf {
            if (hasFocus && enabled) BorderStroke(1.dp, PlanetColors.Solid.red07.color) else null
        }
    }

    Button(
        modifier = modifier
            .height(size.height)
            .onFocusChanged {
                setHasFocus(it.hasFocus || it.isFocused)
            },
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(LocalBorderRadius.current.large),
        colors = ButtonDefaults.buttonColors(
            containerColor = PlanetColors.Solid.blue06.color,
            disabledContainerColor = PlanetColors.Solid.neutralBorder01.color,
            contentColor = PlanetColors.Solid.neutralWhite.color,
            disabledContentColor = PlanetColors.Solid.content03.color
        ),
        elevation = ButtonDefaults.buttonElevation(
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

@Composable
private fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    iconPainter: Painter? = null,
    hasFocus: Boolean,
    setHasFocus: (Boolean) -> Unit,
    enabled: Boolean = true,
    size: PlanetButtonSize = PlanetButtonSize.Large,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    val borderColor by remember {
        derivedStateOf {
            if (hasFocus && enabled) BorderStroke(1.dp, PlanetColors.Solid.red07.color) else null
        }
    }

    val textColor = remember(enabled) {
        if (enabled) PlanetColors.Solid.blue08 else PlanetColors.Solid.content03
    }

    Button(
        modifier = modifier
            .height(size.height)
            .onFocusChanged {
                setHasFocus(it.hasFocus || it.isFocused)
            },
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(LocalBorderRadius.current.large),
        colors = ButtonDefaults.buttonColors(
            containerColor = PlanetColors.Solid.blue01.color,
            disabledContainerColor = PlanetColors.Solid.neutralBg.color,
            contentColor = PlanetColors.Solid.blue08.color,
            disabledContentColor = PlanetColors.Solid.content03.color
        ),
        elevation = ButtonDefaults.buttonElevation(
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

@Composable
private fun TertiaryButton(
    modifier: Modifier = Modifier,
    text: String,
    iconPainter: Painter? = null,
    enabled: Boolean = true,
    hasFocus: Boolean,
    setHasFocus: (Boolean) -> Unit,
    size: PlanetButtonSize = PlanetButtonSize.Medium,
    contentPadding: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
    contentColor: PlanetColors.Solid = PlanetColors.Solid.content03,
    borderColor: PlanetColors.Solid = contentColor,
    backgroundColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    onClick: () -> Unit
) {
    val borderColorByState by remember {
        derivedStateOf {
            if (hasFocus && enabled) PlanetColors.Solid.red07 else borderColor
        }
    }

    OutlinedButton(
        modifier = modifier
            .height(size.height)
            .onFocusChanged {
                setHasFocus(it.hasFocus || it.isFocused)
            },
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(LocalBorderRadius.current.large),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp,
            hoveredElevation = 2.dp,
            focusedElevation = 2.dp
        ),
        border = BorderStroke(1.dp, borderColorByState.color),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor.color,
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
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}