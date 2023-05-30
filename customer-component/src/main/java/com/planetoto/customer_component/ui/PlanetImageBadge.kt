package com.planetoto.customer_component.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.utils.disableRipple

@Composable
fun PlanetImageBadge(
    modifier: Modifier = Modifier,
    imageWidth: Dp = 20.dp,
    imageHeight: Dp = 24.dp,
    badgeCorner: Dp = 20.dp,
    badgeBorder: Dp = 1.5.dp,
    badgeBorderColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    painter: Painter,
    count: Int = 0,
    isRounded: Boolean = false,
    onClick: () -> Unit
) {
    Box(modifier = modifier.disableRipple(onClick = onClick)) {

        val paddingTop = if (count > 0) 8.dp else 0.dp
        val imageModifier = if (isRounded) Modifier
            .padding(top = paddingTop)
            .clip(shape = CircleShape)
            .border(
                width = 1.dp,
                color = PlanetColors.Solid.neutralBorder01.color,
                shape = CircleShape
            ) else Modifier.padding(vertical = 8.dp, horizontal = 10.dp)

        Image(
            modifier = imageModifier.size(width = imageWidth, height = imageHeight),
            painter = painter,
            contentDescription = "ic_image"
        )

        if (count > 0) {
            PlanetBadgeCount(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .align(Alignment.TopStart),
                badgeCorner = badgeCorner,
                badgeBorder = badgeBorder,
                badgeBorderColor = badgeBorderColor,
                count = count
            )
        }
    }
}

@Preview
@Composable
private fun PlanetImageBadgePreview() {
    Box {
        PlanetImageBadge(
            count = 80,
            isRounded = true,
            imageHeight = 32.dp,
            imageWidth = 32.dp,
            painter = painterResource(id = com.planetoto.customer_component.R.drawable.placeholder_avatar)
        ) {

        }
    }
}