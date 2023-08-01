package com.planetoto.customer_component.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import com.planetoto.customer_component.foundation.PlanetColors
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun PlanetImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop,
    imageLoader: ImageLoader? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    contentDescription: String? = null,
    circularReveal: CircularReveal? = null,
    error: Any? = null,
    shimmerBaseColor: PlanetColors.Solid = PlanetColors.Solid.neutralWhite,
    shimmerHighlightColor: PlanetColors.Solid = PlanetColors.Solid.neutralBorder01,
) {
    if (imageLoader != null) {
        CoilImage(
            imageModel = imageUrl,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            contentDescription = contentDescription,
            circularReveal = circularReveal,
            error = error,
            imageLoader = imageLoader,
            shimmerParams = ShimmerParams(
                baseColor = shimmerBaseColor.color,
                highlightColor = shimmerHighlightColor.color
            )
        )
    } else {
        CoilImage(
            imageModel = imageUrl,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            contentDescription = contentDescription,
            circularReveal = circularReveal,
            error = error,
            shimmerParams = ShimmerParams(
                baseColor = shimmerBaseColor.color,
                highlightColor = shimmerHighlightColor.color
            )
        )
    }
}