package com.planetoto.customer_component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.LocalBorderRadius
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.utils.capitalizeWords

@Composable
fun PlanetActionButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    var hasFocus by remember { mutableStateOf(false) }
    val borderColor by remember {
        derivedStateOf {
            when {
                hasFocus && enabled -> PlanetColors.Solid.red07
                enabled -> PlanetColors.Solid.blue02
                else -> PlanetColors.Solid.neutralBorder01
            }
        }
    }

    Button(
        modifier = modifier.onFocusChanged {
            hasFocus = it.hasFocus || it.isFocused
        },
        onClick = onClick,
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 10.dp),
        shape = RoundedCornerShape(LocalBorderRadius.current.large),
        border = BorderStroke(1.dp, borderColor.color),
        colors = ButtonDefaults.buttonColors(
            contentColor = PlanetColors.Solid.blue07.color,
            backgroundColor = PlanetColors.Solid.blue01.color,
            disabledContentColor = PlanetColors.Solid.content03.color,
            disabledBackgroundColor = PlanetColors.Solid.neutralBg.color
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp,
            hoveredElevation = 2.dp,
            focusedElevation = 2.dp
        )
    ) {
        Text(
            text = text.capitalizeWords(),
            fontFamily = FontFamily(Font(R.font.figtree)),
            fontSize = 11.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 13.2.sp,
            letterSpacing = 1.sp
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewActionButton() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        PlanetActionButton(text = "Enabled button") {}
        PlanetActionButton(text = "Disabled button", enabled = false) {}
    }
}