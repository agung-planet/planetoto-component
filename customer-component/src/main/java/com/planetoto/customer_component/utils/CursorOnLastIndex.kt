package com.planetoto.customer_component.utils

import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

val CursorOnLastIndexVisualTransformation = VisualTransformation {
    val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int) = it.text.length
        override fun transformedToOriginal(offset: Int) = it.length
    }
    TransformedText(it, offsetMapping)
}