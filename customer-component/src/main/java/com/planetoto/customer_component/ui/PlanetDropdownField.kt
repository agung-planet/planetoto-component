package com.planetoto.customer_component.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors


@ExperimentalAnimationApi
@Composable
fun PlanetDropDownField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String? = null,
    selectedValue: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Small,
    errorMessage: String? = null
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
        value = selectedValue.orEmpty(),
        onValueChange = {},
        modifier = modifier.onFocusChanged { isFocused = it.isFocused || it.hasFocus },
        singleLine = singleLine,
        textStyle = TextStyle(
            color = PlanetColors.Solid.content02.color,
            fontSize = 12.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.figtree)),
            lineHeight = 14.4.sp
        ),
        readOnly = true,
        maxLines = maxLines,
        decorationBox = { innerTextField ->
            Column {
                PlanetText(text = label)
                Row(
                    modifier = modifier
                        .padding(top = 4.dp)
                        .height(height)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PlanetColors.Solid.neutralWhite.color)
                        .border(
                            width = 1.5.dp,
                            color = borderColor.color,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        if (selectedValue.isNullOrEmpty()) {
                            PlanetText(
                                text = placeholder.orEmpty(),
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                lineHeight = 16.8.sp,
                                color = PlanetColors.Solid.content03
                            )
                        } else {
                            innerTextField()
                        }
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = "see options",
                        tint = PlanetColors.Solid.content02.color
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
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PlanetDropDownField(
            selectedValue = text,
            placeholder = "Type here",
            label = "Search",
            errorMessage = ""
        )
        PlanetDropDownField(
            selectedValue = text,
            placeholder = "Type here",
            label = "Search",
            errorMessage = ""
        )
        PlanetDropDownField(
            selectedValue = text,
            placeholder = "Type here",
            label = "Search",
            errorMessage = ""
        )
    }
}