package com.planetoto.customer_component.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors

@ExperimentalAnimationApi
@Composable
fun PlanetDropDownField(
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    selectedValue: String? = null,
    enabled: Boolean = true,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Large,
    helperText: String? = null,
    isError: Boolean = false,
    isSheetOpen: Boolean = false,
    colors: BaseTextFieldColors = BaseTextFieldColors(),
    onClick: () -> Unit
) {
    val iconTint = remember(enabled) {
        if (enabled) PlanetColors.Solid.content03 else PlanetColors.Solid.neutralBorder01
    }

    BaseTextField(
        modifier = modifier,
        text = selectedValue.orEmpty(),
        onTextChange = {},
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = true,
        singleLine = true,
        size = size,
        isError = isError,
        helperText = helperText,
        colors = colors,
        onClick = onClick,
        suffixBox = {
            val degree by animateFloatAsState(targetValue = if (isSheetOpen) 180f else 0f)
            Box(
                modifier = Modifier.size(it),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_down),
                    contentDescription = "see options",
                    tint = iconTint.color,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(degree)
                )
            }
        }
    )
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun PreviewDropdown() {
    val text by remember {
        mutableStateOf("selected")
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        PlanetDropDownField(
            selectedValue = text,
            placeholder = "Options",
            label = "Enabled",
            onClick = {}
        )
        PlanetDropDownField(
            selectedValue = text,
            placeholder = "Options",
            label = "Disabled",
            enabled = false,
            onClick = {}
        )
    }
}