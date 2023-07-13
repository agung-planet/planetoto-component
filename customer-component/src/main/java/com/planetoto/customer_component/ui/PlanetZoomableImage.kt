package com.planetoto.customer_component.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.mxalbert.zoomable.Zoomable
import com.mxalbert.zoomable.ZoomableState
import com.mxalbert.zoomable.rememberZoomableState

@Composable
fun PlanetZoomableImage(
    zoomableState: ZoomableState = rememberZoomableState(),
    imageUrl: String,
    contentDescription: String?,
    isTablet: Boolean
) {
    var imageFillMaxWidth by remember { mutableStateOf(false) }
    val scale = remember(imageFillMaxWidth) {
        // prevent cropped image
        if (imageFillMaxWidth) ContentScale.FillWidth else ContentScale.FillHeight
    }
    val imageModifier = remember(imageFillMaxWidth) {
        // prevent blank space when zoomed in
        if (imageFillMaxWidth || !isTablet) Modifier.fillMaxWidth() else Modifier
    }

    Zoomable(state = zoomableState) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = scale,
            modifier = imageModifier,
            onState = {
                if (it is AsyncImagePainter.State.Success) {
                    it.painter.intrinsicSize.also { size ->
                        imageFillMaxWidth = size.width >= size.height
                    }
                }
            }
        )
    }
}