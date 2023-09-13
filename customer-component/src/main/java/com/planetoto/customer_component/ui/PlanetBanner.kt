package com.planetoto.customer_component.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planetoto.customer_component.foundation.LocalBorderRadius
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@Composable
fun PlanetBanner(modifier: Modifier = Modifier, icon: Painter, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clip(RoundedCornerShape(LocalBorderRadius.current.large))
            .background(PlanetColors.Solid.blue01.color)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 10.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = text,
            tint = PlanetColors.Solid.blue07.color
        )
        PlanetText(
            text = text,
            typography = PlanetTypography.CaptionLabelOrTag,
            color = PlanetColors.Solid.blue08,
            lineHeight = 13.2.sp
        )
    }
}

@Composable
fun PlanetBanner(
    modifier: Modifier = Modifier,
    icon: Painter,
    text: AnnotatedString,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clip(RoundedCornerShape(LocalBorderRadius.current.large))
            .background(PlanetColors.Solid.blue01.color)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 10.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = text.text,
            tint = PlanetColors.Solid.blue07.color
        )
        PlanetText(
            text = text,
            typography = PlanetTypography.CaptionLabelOrTag,
            color = PlanetColors.Solid.blue08,
            lineHeight = 13.2.sp
        )
    }
}