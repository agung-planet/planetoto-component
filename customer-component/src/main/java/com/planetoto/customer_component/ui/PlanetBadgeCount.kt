package com.planetoto.customer_component.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@Composable
fun PlanetBadgeCount(
    modifier: Modifier = Modifier,
    badgeCorner: Dp = 20.dp,
    badgeBorder: Dp = 1.5.dp,
    badgeBorderColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    badgeBackgroundColor: PlanetColors.Solid = PlanetColors.Solid.red07,
    count: Int = 0
) {
    val mModifier = modifier
        .widthIn(min = 24.dp)
        .border(
            width = badgeBorder,
            color = badgeBorderColor.color,
            shape = CircleShape
        )
        .background(
            color = badgeBackgroundColor.color,
            shape = RoundedCornerShape(badgeCorner)
        )
        .padding(vertical = 3.dp)
    Box(
        modifier = mModifier.padding(horizontal = 6.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        val textCount = if (count > 99) "99+" else count.toString()
        PlanetText(
            text = textCount,
            typography = PlanetTypography.CaptionLabelOrTag,
            color = PlanetColors.Solid.neutralWhite
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PlanetBadgeCount(count = 20)
}