package com.planetoto.customer_component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(LocalBorderRadius.current.large),
        border = BorderStroke(1.dp, borderColor.color),
        colors = ButtonDefaults.buttonColors(
            contentColor = PlanetColors.Solid.blue07.color,
            containerColor = PlanetColors.Solid.blue01.color,
            disabledContentColor = PlanetColors.Solid.content03.color,
            disabledContainerColor = PlanetColors.Solid.neutralBg.color
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp,
            hoveredElevation = 2.dp,
            focusedElevation = 2.dp
        )
    ) {
        Text(
            text = text.capitalizeWords(),
            fontFamily = FontFamily(Font(R.font.figtree_regular)),
            fontSize = 11.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 13.2.sp,
            letterSpacing = 1.sp,
            color = PlanetColors.Solid.blue07.color
        )
        Icon(
            modifier = Modifier.padding(start = 5.dp),
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = PlanetColors.Solid.blue07.color
        )
    }
}