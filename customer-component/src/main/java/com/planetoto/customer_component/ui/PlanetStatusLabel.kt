package com.planetoto.customer_component.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.foundation.PlanetTypography

@Composable
fun PlanetStatusLabel(
    modifier: Modifier = Modifier,
    statusName: String, backgroundColor: PlanetColors.Solid
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor.color,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 2.dp, horizontal = 10.dp)
    ) {
        PlanetText(
            text = statusName,
            typography = PlanetTypography.CaptionLabelOrTag,
            color = PlanetColors.Solid.neutralWhite
        )
    }
}

@Preview
@Composable
private fun PlanetStatusLabelPrev() {
    PlanetStatusLabel(statusName = "Proses Pengajuan", backgroundColor = PlanetColors.Solid.blue05)
}