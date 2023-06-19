package com.planetoto.customer_component.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@Composable
fun PlanetToolbar(
    modifier: Modifier = Modifier,
    isLightToolbar: Boolean = false,
    title: String,
    backgroundColor: PlanetColors.Solid = if (isLightToolbar) PlanetColors.Solid.neutralWhite else PlanetColors.Solid.blue07,
    titleColor: PlanetColors.Solid = if (isLightToolbar) PlanetColors.Solid.content01 else PlanetColors.Solid.neutralWhite,
    navigateUpIconColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    showNavigateUp: Boolean = false,
    navigateUpIcon: @Composable () -> Unit = {
        if (isLightToolbar) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_back_rounded),
                contentDescription = "ic_back"
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left_16),
                contentDescription = "up",
                tint = navigateUpIconColor.color,
                modifier = Modifier.size(24.dp)
            )
        }
    },
    onNavigateUp: (() -> Unit)? = null
) {
    PlanetToolbar(
        modifier = modifier,
        title = {
            PlanetText(
                text = title,
                color = titleColor,
                typography = PlanetTypography.TitleBody,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 50.dp)
            )
        },
        backgroundColor = backgroundColor,
        showNavigateUp = showNavigateUp,
        navigateUpIcon = navigateUpIcon,
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun PlanetToolbar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    backgroundColor: PlanetColors.Solid = PlanetColors.Solid.blue07,
    navigateUpIconColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    showNavigateUp: Boolean = false,
    navigateUpIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left_16),
            contentDescription = "up",
            tint = navigateUpIconColor.color,
            modifier = Modifier.size(24.dp)
        )
    },
    onNavigateUp: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor.color)
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .heightIn(min = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showNavigateUp) {
            if (onNavigateUp == null) throw IllegalArgumentException("must implement onNavigateUp!")
            IconButton(
                onClick = onNavigateUp,
                content = navigateUpIcon,
                modifier = Modifier.padding(end = 10.dp)
            )
        }

        title()
    }
}

@Composable
@Preview
fun PPreview() {
    PlanetToolbar(title = "AAAAAAAAAAAAAAAAA", isLightToolbar = true, showNavigateUp = true) {

    }
}