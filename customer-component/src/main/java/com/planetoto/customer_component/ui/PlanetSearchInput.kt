package com.planetoto.customer_component.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@ExperimentalAnimationApi
@Composable
fun PlanetSearchInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    label: String,
    errorMessage: String? = null,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Small,
    enabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }
    val height = remember(size) {
        when (size) {
            PlanetTextFieldSize.Small -> 40.dp
            PlanetTextFieldSize.Large -> 50.dp
        }
    }
    val borderColor by remember(isFocused, errorMessage) {
        derivedStateOf {
            when {
                !enabled -> PlanetColors.Solid.neutralBorder02
                isFocused -> PlanetColors.Solid.blue05
                !errorMessage.isNullOrEmpty() -> PlanetColors.Solid.red05
                else -> PlanetColors.Solid.neutralBorder01
            }
        }
    }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier.onFocusChanged { isFocused = it.isFocused || it.hasFocus },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }),
        textStyle = TextStyle(
            color = PlanetColors.Solid.content02.color,
            fontSize = 12.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.figtree)),
            lineHeight = 14.4.sp
        ),
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier = modifier
                        .height(height)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PlanetColors.Solid.neutralWhite.color)
                        .border(
                            width = 1.5.dp,
                            color = borderColor.color,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        val textSize by animateIntAsState(targetValue = if (isFocused) 9 else 12)
                        val lineHeight by animateFloatAsState(targetValue = if (isFocused) 10.8f else 14.4f)
                        PlanetText(
                            text = label,
                            fontWeight = FontWeight.W400,
                            fontSize = textSize.sp,
                            lineHeight = lineHeight.sp,
                            color = PlanetColors.Solid.content03
                        )
                        if (isFocused) {
                            innerTextField()
                        }
                    }
                    AnimatedVisibility(
                        visible = text.isNotEmpty(),
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close_rounded),
                            contentDescription = "clear text",
                            modifier = Modifier.clickable { onTextChange("") },
                            tint = PlanetColors.Solid.content03.color
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search",
                        tint = PlanetColors.Solid.content03.color,
                        modifier = Modifier
                            .clickable { onSearchClicked(text) }
                            .padding(start = 10.dp)
                    )
                }
                AnimatedVisibility(
                    visible = !errorMessage.isNullOrEmpty(),
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {
                    PlanetText(
                        text = errorMessage!!,
                        typography = PlanetTypography.CaptionLabelOrTag,
                        lineHeight = 20.sp,
                        color = PlanetColors.Solid.red05
                    )
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
        mutableStateOf("abc")
    }

    Box(modifier = Modifier.padding(16.dp)) {
        PlanetSearchInput(
            text = text,
            onTextChange = { text = it },
            onSearchClicked = {},
            label = "Search",
            errorMessage = "An error occurred"
        )
    }
}