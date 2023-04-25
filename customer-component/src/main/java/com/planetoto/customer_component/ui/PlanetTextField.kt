package com.planetoto.customer_component.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors

enum class PlanetTextFieldSize {
    Small, Large
}

/**
 * Editable, non clickable by default
 */
@ExperimentalAnimationApi
@Composable
fun PlanetTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    placeholder: String? = null,
    prefix: Painter? = null,
    suffix: Painter? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Small,
    isError: Boolean = false,
    helperText: String? = null,
    hasClearAction: Boolean = false
) {
    BaseTextField(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        size = size,
        helperText = helperText,
        isError = isError,
        hasClearAction = hasClearAction,
        prefixBox = if (prefix != null) {
            {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(it)
                        .fillMaxHeight()
                        .background(PlanetColors.Solid.neutralBg.color)
                        .border(1.dp, PlanetColors.Solid.neutralBorder01.color)
                ) {
                    Icon(
                        painter = prefix,
                        contentDescription = "prefix",
                        tint = PlanetColors.Solid.blue07.color
                    )
                }
            }
        } else null,
        suffixBox = if (suffix != null) {
            {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(it)
                        .fillMaxHeight()
                        .background(PlanetColors.Solid.neutralBg.color)
                        .border(1.dp, PlanetColors.Solid.neutralBorder01.color)
                ) {
                    Icon(
                        painter = suffix,
                        contentDescription = "suffix",
                        tint = PlanetColors.Solid.blue07.color
                    )
                }
            }
        } else null
    )
}

/**
 * Non editable, clickable by default
 */
@ExperimentalAnimationApi
@Composable
fun PlanetTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    placeholder: String? = null,
    prefix: Painter? = null,
    suffix: Painter? = null,
    enabled: Boolean = true,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Small,
    isError: Boolean = false,
    helperText: String? = null,
    hasClearAction: Boolean = false,
    onClick: () -> Unit
) {
    BaseTextField(
        modifier = modifier,
        text = text,
        onTextChange = {},
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = true,
        singleLine = true,
        size = size,
        helperText = helperText,
        isError = isError,
        hasClearAction = hasClearAction,
        onClick = onClick,
        prefixBox = if (prefix != null) {
            {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(it)
                        .fillMaxHeight()
                        .background(PlanetColors.Solid.neutralBg.color)
                        .border(1.dp, PlanetColors.Solid.neutralBorder01.color)
                ) {
                    Icon(
                        painter = prefix,
                        contentDescription = "prefix",
                        tint = PlanetColors.Solid.blue07.color
                    )
                }
            }
        } else null,
        suffixBox = if (suffix != null) {
            {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(it)
                        .fillMaxHeight()
                        .background(PlanetColors.Solid.neutralBg.color)
                        .border(1.dp, PlanetColors.Solid.neutralBorder01.color)
                ) {
                    Icon(
                        painter = suffix,
                        contentDescription = "suffix",
                        tint = PlanetColors.Solid.blue07.color
                    )
                }
            }
        } else null
    )
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun PreviewSearchInput() {
    var text by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PlanetTextField(
            text = text,
            onTextChange = { text = it },
            placeholder = "Type here",
            label = "Search",
            hasClearAction = true,
            isError = false
        )
        PlanetTextField(
            text = text,
            onTextChange = { text = it },
            placeholder = "Type here",
            label = "Search",
            prefix = painterResource(id = R.drawable.ic_search),
            hasClearAction = true,
            isError = true
        )
        PlanetTextField(
            text = text,
            onTextChange = { text = it },
            placeholder = "Type here",
            label = "Search",
            suffix = painterResource(id = R.drawable.ic_search),
            hasClearAction = true,
            isError = true
        )
    }
}