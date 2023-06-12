package com.planetoto.customer_component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.utils.CursorOnLastIndexVisualTransformation
import com.planetoto.customer_component.utils.updateElement

data class OtpFieldModel(
    val index: Int,
    val value: String,
    val shouldEnable: Boolean
)

@ExperimentalComposeUiApi
@Composable
fun PlanetOtpTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onComplete: () -> Unit
) {
    var otpFieldModels by remember {
        mutableStateOf(
            listOf(
                OtpFieldModel(index = 0, value = "", shouldEnable = true),
                OtpFieldModel(index = 1, value = "", shouldEnable = false),
                OtpFieldModel(index = 2, value = "", shouldEnable = false),
                OtpFieldModel(index = 3, value = "", shouldEnable = false)
            )
        )
    }

    var otpSent by remember { mutableStateOf(false) }
    var isTapBackSpace = false

    val focusManager = LocalFocusManager.current
    val (first, second, third, fourth) = FocusRequester.createRefs()
    var everFullFilledOtp by remember { mutableStateOf(false) }

    val space = 12.dp

    LaunchedEffect(otpFieldModels) {
        if (otpFieldModels.any { it.value.isEmpty() }) {
            if (isTapBackSpace) focusManager.moveFocus(FocusDirection.Up)
            else focusManager.moveFocus(FocusDirection.Down)
        } else {
            everFullFilledOtp = true
            focusManager.clearFocus(true)
        }
    }

    LaunchedEffect(value) {
        if (value.isEmpty()) {
            otpFieldModels = listOf(
                OtpFieldModel(index = 0, value = "", shouldEnable = true),
                OtpFieldModel(index = 1, value = "", shouldEnable = false),
                OtpFieldModel(index = 2, value = "", shouldEnable = false),
                OtpFieldModel(index = 3, value = "", shouldEnable = false)
            )
        }
    }

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(space)) {
        repeat(4) { i ->
            val otpCodeFieldModifier = Modifier.weight(1f).let {
                when (i) {
                    0 -> it
                        .focusRequester(first)
                        .focusProperties {
                            down = second
                        }

                    1 -> it
                        .focusRequester(second)
                        .focusProperties {
                            down = third
                            up = first
                        }

                    2 -> it
                        .focusRequester(third)
                        .focusProperties {
                            down = fourth
                            up = second
                        }

                    3 -> it
                        .focusRequester(fourth)
                        .focusProperties {
                            up = third
                        }

                    else -> it
                }
            }

            OtpCodeTextField(
                modifier = otpCodeFieldModifier,
                focusManager = focusManager,
                isError = isError,
                value = otpFieldModels[i].value,
                shouldEnable = otpFieldModels[i].shouldEnable,
                isLastIndex = i == 3,
                onBackSpaceTap = {
                    if (otpFieldModels[0].value.isEmpty() || (everFullFilledOtp && i==3)){
                        everFullFilledOtp = false
                        return@OtpCodeTextField
                    }
                    isTapBackSpace = true
                    otpFieldModels = otpFieldModels.updateElement({ it.index == i - 1 }) {
                        it.copy(value = "", shouldEnable = true)
                    }.let {
                        it.updateElement({ m -> m.index == i }) { mod ->
                            mod.copy(shouldEnable = false)
                        }
                    }
                },
                onValueChanged = { newStr ->
                    try {
                        isTapBackSpace = false
                        val tmp = otpFieldModels.updateElement({ it.index == i }) {
                            val shouldEnable = i == 3
                            it.copy(value = newStr, shouldEnable = shouldEnable)
                        }.let {
                            if (i == 3 && newStr.isEmpty()) it
                            else {
                                if (it.all { f -> f.value.isNotEmpty() }) {
                                    it
                                } else {
                                    val nextIndex = it.first { m -> m.value.isEmpty() }.index
                                    it.updateElement({ m -> m.index == nextIndex }) { mod ->
                                        mod.copy(shouldEnable = true)
                                    }
                                }
                            }
                        }

                        val str = tmp.joinToString(separator = "") { it.value }
                        onValueChanged(str)

                        otpFieldModels = tmp

                        otpSent = false
                    } catch (e: Exception) {
                    }
                }
            )

            if (value.length == 4 && !value.contains(Char.MIN_VALUE) && !otpSent) {
                onComplete()
                otpSent = true
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun OtpCodeTextField(
    value: String,
    shouldEnable: Boolean,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    isLastIndex: Boolean,
    onBackSpaceTap: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColorId = when {
        isError -> PlanetColors.Solid.red07
        isFocused -> PlanetColors.Solid.contentLink
        value.isNotBlank() -> PlanetColors.Solid.neutralBorder02
        else -> PlanetColors.Solid.neutralBorder01
    }
    val backgroundId = PlanetColors.Solid.neutralWhite

    BasicTextField(
        value = value,
        onValueChange = { s ->
            val filtered = s.filter { it != Char.MIN_VALUE }
            if (filtered.length <= 1) {
                onValueChanged(filtered)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        singleLine = true,
        textStyle = TextStyle(
            color = PlanetColors.Solid.content01.color,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font((R.font.figtree))),
            lineHeight = 38.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(700)
        ),
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundId.color)
            .border(BorderStroke(1.dp, borderColorId.color), RoundedCornerShape(10.dp))
            .onFocusChanged {
                isFocused = it.isFocused || it.hasFocus
            }
            .onKeyEvent {
                val isBackspace = it.type == KeyEventType.KeyUp && it.key == Key.Backspace
                val isEmpty = value.isBlank() || value.contains(Char.MIN_VALUE)

                if ((isBackspace && isEmpty) || (isLastIndex && isBackspace)) {
                    onBackSpaceTap()
                    true
                } else false
            },
        visualTransformation = CursorOnLastIndexVisualTransformation,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center) {
                innerTextField()
            }
        },
        enabled = shouldEnable
    )
}