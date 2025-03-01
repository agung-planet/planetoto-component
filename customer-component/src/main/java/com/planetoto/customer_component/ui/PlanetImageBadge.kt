package com.planetoto.customer_component.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.utils.disableRipple

@Composable
fun PlanetImageBadge(
    modifier: Modifier = Modifier,
    imageWidth: Dp = 20.dp,
    imageHeight: Dp = 24.dp,
    badgeSize: Dp = 16.dp,
    badgeBorder: Dp = 1.5.dp,
    badgeBorderColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    painter: Painter,
    count: Int = 0,
    isRounded: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val mModifier = if (onClick == null) modifier else modifier.disableRipple(onClick = onClick)
    ConstraintLayout(modifier = mModifier) {
        val (icon, badge) = createRefs()

        val paddingTop = if (count > 0) 8.dp else 0.dp
        val imageModifier = if (isRounded) Modifier
            .clip(shape = CircleShape)
            .border(
                width = 1.dp, color = PlanetColors.Solid.neutralBorder01.color, shape = CircleShape
            ) else Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)

        Image(modifier = imageModifier
            .constrainAs(icon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .size(width = imageWidth, height = imageHeight),
            painter = painter,
            contentDescription = "ic_image")

        val badgeEndPadding = if (count < 10) 0.dp else if (count > 99) (-10).dp else (-5).dp

        if (count > 0) {
            PlanetBadgeCount(
                modifier = Modifier.constrainAs(badge) {
                    top.linkTo(parent.top)
                    end.linkTo(icon.end, badgeEndPadding)
                },
                size = badgeSize,
                badgeBorder = badgeBorder,
                badgeBorderColor = badgeBorderColor,
                count = count
            )
        }
    }
}

@Composable
fun PlanetImageBadge(
    modifier: Modifier = Modifier,
    iconWidth: Dp = 20.dp,
    iconHeight: Dp = 24.dp,
    badgeSize: Dp = 16.dp,
    badgeBorder: Dp = 1.5.dp,
    badgeBorderColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    iconPainter: Painter,
    iconTint: PlanetColors.Solid,
    count: Int = 0,
    isRounded: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val mModifier = if (onClick == null) modifier else modifier.disableRipple(onClick = onClick)
    ConstraintLayout(modifier = mModifier) {
        val (icon, badge) = createRefs()

        val paddingTop = if (count > 0) 8.dp else 0.dp
        val imageModifier = if (isRounded) Modifier
            .clip(shape = CircleShape)
            .border(
                width = 1.dp, color = PlanetColors.Solid.neutralBorder01.color, shape = CircleShape
            ) else Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)

        Icon(modifier = imageModifier
            .constrainAs(icon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            .size(width = iconWidth, height = iconHeight),
            painter = iconPainter,
            tint = iconTint.color,
            contentDescription = "ic_image")
        val badgeEndPadding = if (count < 10) 0.dp else if (count > 99) (-10).dp else (-5).dp

        if (count > 0) {
            PlanetBadgeCount(
                modifier = Modifier.constrainAs(badge) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, badgeEndPadding)
                },
                size = badgeSize,
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
    Column {
        Box {
            PlanetImageBadge(
                count = 10,
                iconHeight = 32.dp,
                iconWidth = 32.dp,
                iconTint = PlanetColors.Solid.blue07,
                iconPainter = painterResource(id = com.planetoto.customer_component.R.drawable.placeholder_avatar)
            ) {

            }
        }
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
}