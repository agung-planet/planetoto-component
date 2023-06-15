package com.planetoto.customer_component.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * @param size text size in sp/em
 * @param lineHeight line height in sp/em. null if set to auto
 * @param weight font weight
 */
@Immutable
sealed class PlanetTypography(
    val size: TextUnit,
    val lineHeight: TextUnit?,
    val weight: FontWeight?
) {
    object Headline : PlanetTypography(size = 32.sp, lineHeight = 38.5.sp, weight = FontWeight.Bold)

    object TitleScreen :
        PlanetTypography(size = 26.sp, lineHeight = 32.sp, weight = FontWeight.Bold)

    object TitleSection :
        PlanetTypography(size = 22.sp, lineHeight = 28.sp, weight = FontWeight.Bold)

    object TitleSubsection :
        PlanetTypography(size = 18.sp, lineHeight = 24.sp, weight = FontWeight.SemiBold)

    object TitleBody : PlanetTypography(size = 16.sp, lineHeight = 24.sp, weight = FontWeight.Bold)
    object SubtitleBody :
        PlanetTypography(size = 14.sp, lineHeight = 22.sp, weight = FontWeight.SemiBold)

    object CardTitle : PlanetTypography(size = 14.sp, lineHeight = 22.sp, weight = FontWeight.Bold)
    object BodyLarge :
        PlanetTypography(size = 16.sp, lineHeight = 27.sp, weight = FontWeight.Normal)

    object BodyLargeBold :
        PlanetTypography(size = 16.sp, lineHeight = 27.sp, weight = FontWeight.Bold)

    object BodyDefault14 :
        PlanetTypography(size = 14.sp, lineHeight = 24.sp, weight = FontWeight.Normal)

    object BodyDefaultBold :
        PlanetTypography(size = 14.sp, lineHeight = 24.sp, weight = FontWeight.Bold)

    object SmallBody :
        PlanetTypography(size = 12.sp, lineHeight = 22.sp, weight = FontWeight.Normal)

    object CaptionHelper :
        PlanetTypography(size = 11.sp, lineHeight = 20.sp, weight = FontWeight.Normal)

    object CaptionUppercase :
        PlanetTypography(size = 11.sp, lineHeight = null, weight = FontWeight.SemiBold)

    object CaptionLabelOrTag :
        PlanetTypography(size = 11.sp, lineHeight = null, weight = FontWeight.SemiBold)

    object CaptionSmallLabel :
        PlanetTypography(size = 9.sp, lineHeight = null, weight = FontWeight.SemiBold)

    object CaptionCodeType :
        PlanetTypography(size = 14.sp, lineHeight = 24.sp, weight = FontWeight.Normal)

    object LabelBigButton :
        PlanetTypography(size = 14.sp, lineHeight = null, weight = FontWeight.SemiBold)

    object LabelMediumButton :
        PlanetTypography(size = 12.sp, lineHeight = null, weight = FontWeight.Bold)
}