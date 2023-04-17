package com.planetoto.customer_component.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors

enum class PlanetTextFieldSize {
    Small, Large
}

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
    errorMessage: String? = null,
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
        errorMessage = errorMessage,
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

@ExperimentalAnimationApi
@Composable
internal fun BaseTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    placeholder: String? = null,
    prefixBox: (@Composable (Dp) -> Unit)? = null,
    suffixBox: (@Composable (Dp) -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Small,
    errorMessage: String? = null,
    hasClearAction: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val height = remember(size) {
        when (size) {
            PlanetTextFieldSize.Small -> 40.dp
            PlanetTextFieldSize.Large -> 50.dp
        }
    }
    val borderColor by remember {
        derivedStateOf {
            when {
                !enabled -> PlanetColors.Solid.neutralBorder02
                isFocused -> PlanetColors.Solid.blue05
                errorMessage != null -> PlanetColors.Solid.red05
                else -> PlanetColors.Solid.neutralBorder01
            }
        }
    }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier.onFocusChanged { isFocused = it.isFocused || it.hasFocus },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = TextStyle(
            color = PlanetColors.Solid.content02.color,
            fontSize = 12.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.figtree)),
            lineHeight = 14.4.sp
        ),
        readOnly = readOnly,
        maxLines = maxLines,
        enabled = enabled,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Column {
                PlanetText(text = label)
                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .height(height)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PlanetColors.Solid.neutralWhite.color)
                        .border(
                            width = 1.5.dp,
                            color = borderColor.color,
                            shape = RoundedCornerShape(8.dp)
                        ).run {
                            if (onClick != null) clickable(onClick = onClick) else this
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    prefixBox?.invoke(height)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = placeholder != null && text.isEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            PlanetText(
                                text = placeholder.orEmpty(),
                                lineHeight = 16.8.sp,
                                color = PlanetColors.Solid.content03
                            )
                        }
                        innerTextField()
                    }
                    AnimatedVisibility(
                        visible = text.isNotEmpty() && hasClearAction,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close_rounded),
                            contentDescription = "clear text",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onTextChange("") },
                            tint = PlanetColors.Solid.content03.color
                        )
                    }

                    suffixBox?.invoke(height)
                }
            }
        }
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
            errorMessage = ""
        )
        PlanetTextField(
            text = text,
            onTextChange = { text = it },
            placeholder = "Type here",
            label = "Search",
            prefix = painterResource(id = R.drawable.ic_search),
            hasClearAction = true,
            errorMessage = ""
        )
        PlanetTextField(
            text = text,
            onTextChange = { text = it },
            placeholder = "Type here",
            label = "Search",
            suffix = painterResource(id = R.drawable.ic_search),
            hasClearAction = true,
            errorMessage = ""
        )
    }
}