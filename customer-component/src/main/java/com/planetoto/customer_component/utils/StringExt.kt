package com.planetoto.customer_component.utils

import java.util.*

internal fun String.capitalizeWords() = split(" ").joinToString(separator = " ") {
    it.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase(Locale.ROOT) else c.toString() }
}