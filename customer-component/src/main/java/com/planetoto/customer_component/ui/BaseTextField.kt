package com.planetoto.customer_component.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.LocalPadding
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@ExperimentalAnimationApi
@Composable
internal fun BaseTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String?,
    placeholder: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Small,
    helperText: String? = null,
    isError: Boolean = false,
    hasClearAction: Boolean = false,
    onTextChange: (String) -> Unit,
    prefixBox: (@Composable (Dp) -> Unit)? = null,
    suffixBox: (@Composable (Dp) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val height = remember(size) {
        when (size) {
            PlanetTextFieldSize.Small -> 40.dp
            PlanetTextFieldSize.Large -> 50.dp
        }
    }
    val borderColor by remember(enabled, isError) {
        derivedStateOf {
            when {
                isError -> PlanetColors.Solid.red05
                !enabled -> PlanetColors.Solid.neutralBorder02
                isFocused -> PlanetColors.Solid.blue05
                else -> PlanetColors.Solid.neutralBorder01
            }
        }
    }
    val background by remember(enabled, isError) {
        derivedStateOf {
            if (enabled) PlanetColors.Solid.neutralWhite else PlanetColors.Solid.neutralBg
        }
    }
    val helperTextColor = remember(isError) {
        if (isError) PlanetColors.Solid.red05 else PlanetColors.Solid.content03
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
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.figtree)),
            lineHeight = 16.8.sp
        ),
        readOnly = readOnly,
        maxLines = maxLines,
        enabled = enabled,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Column {
                label?.let {
                    PlanetText(text = it, modifier = Modifier.padding(bottom = 4.dp))
                }
                Row(
                    modifier = Modifier
                        .height(height)
                        .clip(RoundedCornerShape(8.dp))
                        .background(background.color)
                        .border(
                            width = 1.5.dp,
                            color = borderColor.color,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .then(
                            if (enabled && onClick != null) {
                                Modifier.clickable(onClick = onClick)
                            } else Modifier
                        ),
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
                        exit = scaleOut(),
                        modifier = Modifier.padding(end = LocalPadding.current.xSmall)
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
                helperText?.let {
                    PlanetText(
                        text = it,
                        typography = PlanetTypography.CaptionHelper,
                        color = helperTextColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    )
}

@ExperimentalAnimationApi
@Composable
internal fun BaseTextField(
    modifier: Modifier = Modifier,
    text: TextFieldValue,
    label: String?,
    placeholder: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Small,
    helperText: String? = null,
    isError: Boolean = false,
    hasClearAction: Boolean = false,
    onTextChange: (TextFieldValue) -> Unit,
    prefixBox: (@Composable (Dp) -> Unit)? = null,
    suffixBox: (@Composable (Dp) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val height = remember(size) {
        when (size) {
            PlanetTextFieldSize.Small -> 40.dp
            PlanetTextFieldSize.Large -> 50.dp
        }
    }
    val borderColor by remember(enabled, isError) {
        derivedStateOf {
            when {
                !enabled -> PlanetColors.Solid.neutralBorder02
                isFocused -> PlanetColors.Solid.blue05
                isError -> PlanetColors.Solid.red05
                else -> PlanetColors.Solid.neutralBorder01
            }
        }
    }
    val helperTextColor = remember(isError) {
        if (isError) PlanetColors.Solid.red05 else PlanetColors.Solid.content03
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
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.figtree)),
            lineHeight = 16.8.sp
        ),
        readOnly = readOnly,
        maxLines = maxLines,
        enabled = enabled,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Column {
                label?.let {
                    PlanetText(text = it, modifier = Modifier.padding(bottom = 4.dp))
                }
                Row(
                    modifier = Modifier
                        .height(height)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PlanetColors.Solid.neutralWhite.color)
                        .border(
                            width = 1.5.dp,
                            color = borderColor.color,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .then(
                            if (enabled && onClick != null) {
                                Modifier.clickable(onClick = onClick)
                            } else Modifier
                        ),
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
                            visible = placeholder != null && text.text.isEmpty(),
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
                        visible = text.text.isNotEmpty() && hasClearAction && isFocused,
                        enter = scaleIn(),
                        exit = scaleOut(),
                        modifier = Modifier.padding(end = LocalPadding.current.xSmall)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close_rounded),
                            contentDescription = "clear text",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onTextChange(TextFieldValue(text = "")) },
                            tint = PlanetColors.Solid.content03.color
                        )
                    }

                    suffixBox?.invoke(height)
                }
                helperText?.let {
                    PlanetText(
                        text = it,
                        typography = PlanetTypography.CaptionHelper,
                        color = helperTextColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    )
}