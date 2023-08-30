package com.planetoto.customer_component.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@Composable
fun PlanetCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String?,
    isError: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    textColor: PlanetColors.Solid = PlanetColors.Solid.content01
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CheckboxIndicator(
            checked = checked,
            onCheckedChange = onCheckedChange,
            isError = isError
        )
        text?.let {
            PlanetText(
                text = it,
                color = textColor,
                modifier = Modifier
                    .clickable { onCheckedChange(!checked) }
                    .padding(end = 8.dp),
                typography = if (checked) PlanetTypography.BodyDefaultBold else PlanetTypography.BodyDefault14
            )
        }
    }
}

@Composable
private fun CheckboxIndicator(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isError: Boolean = false
) {
    val background = remember(checked) {
        if (checked) PlanetColors.Solid.green06 else PlanetColors.Solid.neutralWhite
    }
    val bgAnimation by animateColorAsState(targetValue = background.color)
    val borderColor by remember(checked, isError) {
        derivedStateOf {
            when {
                checked -> PlanetColors.Solid.green06
                isError -> PlanetColors.Solid.red07
                else -> PlanetColors.Solid.neutralBorder02
            }
        }
    }
    val borderColorAnimation by animateColorAsState(targetValue = borderColor.color)

    Box(
        modifier = modifier
            .size(16.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(bgAnimation)
            .border(1.dp, borderColorAnimation, RoundedCornerShape(4.dp))
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = checked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = PlanetColors.Solid.neutralWhite.color
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCheckbox() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PlanetCheckbox(
            checked = true,
            onCheckedChange = {},
            text = "Checked"
        )
        PlanetCheckbox(
            checked = false,
            onCheckedChange = {},
            text = "Unchecked"
        )
        PlanetCheckbox(
            checked = false,
            onCheckedChange = {},
            text = "Error",
            isError = true
        )
    }
}