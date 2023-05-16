package com.planetoto.customer_component.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object PlanetColors {
    @Immutable
    @JvmInline
    value class Solid(val color: Color) {
        companion object {
            // Content color
            val content01 = Solid(Color(0xFF111111))
            val content02 = Solid(Color(0xFF596266))
            val content03 = Solid(Color(0xFF91989E))
            val contentLink = Solid(Color(0xFF0D68FD))

            // Background & border
            val neutralWhite = Solid(Color(0xFFFFFFFF))
            val neutralBg = Solid(Color(0xFFF7F8F9))
            val neutralBorder01 = Solid(Color(0xFFE6E7E8))
            val neutralBorder02 = Solid(Color(0xFFBCC0C4))

            // Primary red
            val red01 = Solid(Color(0xFFFFF3F2))
            val red02 = Solid(Color(0xFFFFDDD9))
            val red03 = Solid(Color(0xFFF7B5B3))
            val red04 = Solid(Color(0xFFF4817B))
            val red05 = Solid(Color(0xFFEC4E49))
            val red06 = Solid(Color(0xFFE9322C))
            val red07 = Solid(Color(0xFFE1251B))
            val red08 = Solid(Color(0xFFC01F16))
            val red09 = Solid(Color(0xFFA01A13))
            val red10 = Solid(Color(0xFF660000))

            // Primary blue
            val blue01 = Solid(Color(0xFFEFF5FF))
            val blue02 = Solid(Color(0xFFCEE1FF))
            val blue03 = Solid(Color(0xFF99BFFF))
            val blue04 = Solid(Color(0xFF498FFF))
            val blue05 = Solid(Color(0xFF0D68FD))
            val blue06 = Solid(Color(0xFF004FD0))
            val blue07 = Solid(Color(0xFF0047BA))
            val blue08 = Solid(Color(0xFF003C9D))
            val blue09 = Solid(Color(0xFF002869))
            val blue10 = Solid(Color(0xFF021740))

            // Functional yellow
            val yellow01 = Solid(Color(0xFFFFFCDB))
            val yellow02 = Solid(Color(0xFFFFF9B6))
            val yellow03 = Solid(Color(0xFFFFF477))
            val yellow04 = Solid(Color(0xFFFFF477))
            val yellow05 = Solid(Color(0xFFFFDF3F))
            val yellow06 = Solid(Color(0xFFFFD60A))
            val yellow07 = Solid(Color(0xFFFFBF00))
            val yellow08 = Solid(Color(0xFFEDB200))
            val yellow09 = Solid(Color(0xFFA47B00))
            val yellow10 = Solid(Color(0xFF403101))

            // Functional green
            val green01 = Solid(Color(0xFFDFF6E1))
            val green02 = Solid(Color(0xFFBFEDC4))
            val green03 = Solid(Color(0xFF8FE097))
            val green04 = Solid(Color(0xFF5DCB55))
            val green05 = Solid(Color(0xFF32BB3F))
            val green06 = Solid(Color(0xFF30B03C))
            val green07 = Solid(Color(0xFF019D48))
            val green08 = Solid(Color(0xFF017C38))
            val green09 = Solid(Color(0xFF005A29))
            val green10 = Solid(Color(0xFF003819))

            // Functional turquoise
            val turqoise01 = Solid(Color(0xFFE8FCFF))
            val turqoise02 = Solid(Color(0xFFC3F7FF))
            val turqoise03 = Solid(Color(0xFFA0F2FF))
            val turqoise04 = Solid(Color(0xFF5CE9FF))
            val turqoise05 = Solid(Color(0xFF0FDFFF))
            val turqoise06 = Solid(Color(0xFF00C8E6))
            val turqoise07 = Solid(Color(0xFF00A1BA))
            val turqoise08 = Solid(Color(0xFF00889D))
            val turqoise09 = Solid(Color(0xFF006676))
            val turqoise10 = Solid(Color(0xFF003740))

            // Monochrome
            val monochrome600 = Solid(Color(0xFF838B92))

            //Transparent
            val transparent = Solid(Color.Transparent)
        }

        fun alpha(value: Float): Solid = Solid(color.copy(alpha = value))
    }

    @Immutable
    @JvmInline
    value class Gradient(val brush: Brush) {
        companion object {
            val linearRed = Gradient(
                Brush.linearGradient(listOf(Color(0xFFD81600), Color(0xFFFF7E70)))
            )
            val linearBlue = Gradient(
                Brush.linearGradient(listOf(Color(0xFF0652E0), Color(0xFF6096FB)))
            )
            val linearYellow = Gradient(
                Brush.linearGradient(listOf(Color(0xFFFFBF00), Color(0xFFFFDF3F)))
            )
            val linearGreen = Gradient(
                Brush.linearGradient(listOf(Color(0xFF30B03C), Color(0xFF8FE097)))
            )
            val linearTosca = Gradient(
                Brush.linearGradient(listOf(Color(0xFF00A1BA), Color(0xFF5CE9FF)))
            )
            val linearDuoTone1 = Gradient(
                Brush.linearGradient(listOf(Color(0xFF0542B2), Color(0xFFED1400)))
            )
            val linearDuoTone2 = Gradient(
                Brush.linearGradient(listOf(Color(0xFF32BB3F), Color(0xFF3278FA)))
            )
            val linearDuoTone3 = Gradient(
                Brush.linearGradient(listOf(Color(0xFF00C8E6), Color(0xFF0542B2)))
            )
        }
    }
}
