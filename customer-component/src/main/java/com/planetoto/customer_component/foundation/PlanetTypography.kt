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
    object Headline : PlanetTypography(size = 32.sp, lineHeight = 38.5.sp, weight = FontWeight.W700)
    object TitleScreen : PlanetTypography(size = 26.sp, lineHeight = 32.sp, weight = FontWeight.W700)
    object TitleSection : PlanetTypography(size = 22.sp, lineHeight = 28.sp, weight = FontWeight.W700)
    object TitleSubsection : PlanetTypography(size = 18.sp, lineHeight = 24.sp, weight = FontWeight.W600)
    object TitleBody : PlanetTypography(size = 18.sp, lineHeight = 24.sp, weight = FontWeight.W700)
    object SubtitleBody : PlanetTypography(size = 14.sp, lineHeight = 22.sp, weight = FontWeight.W600)
    object CardTitle : PlanetTypography(size = 14.sp, lineHeight = 22.sp, weight = FontWeight.W700)
    object BodyLarge : PlanetTypography(size = 16.sp, lineHeight = 27.sp, weight = FontWeight.W400)
    object BodyLargeBold : PlanetTypography(size = 16.sp, lineHeight = 27.sp, weight = FontWeight.W700)
    object BodyDefault14 : PlanetTypography(size = 14.sp, lineHeight = 24.sp, weight = FontWeight.W400)
    object BodyDefaultBold : PlanetTypography(size = 14.sp, lineHeight = 24.sp, weight = FontWeight.W700)
    object BodyDefault12 : PlanetTypography(size = 12.sp, lineHeight = 22.sp, weight = FontWeight.W400)
    object CaptionHelper : PlanetTypography(size = 12.sp, lineHeight = 22.sp, weight = FontWeight.W400)
    object CaptionUppercase : PlanetTypography(size = 11.sp, lineHeight = null, weight = FontWeight.W600)
    object CaptionLabelOrTag : PlanetTypography(size = 11.sp, lineHeight = null, weight = FontWeight.W600)
    object CaptionCodeType : PlanetTypography(size = 14.sp, lineHeight = 24.sp, weight = FontWeight.W400)
    object LabelMediumButton : PlanetTypography(size = 14.sp, lineHeight = null, weight = FontWeight.W600)
    object LabelSmallButton : PlanetTypography(size = 12.sp, lineHeight = null, weight = FontWeight.W700)
}