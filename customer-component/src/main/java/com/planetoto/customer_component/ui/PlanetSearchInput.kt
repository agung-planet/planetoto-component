package com.planetoto.customer_component.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planetoto.customer_component.R
import com.planetoto.customer_component.foundation.LocalPadding
import com.planetoto.customer_component.foundation.PlanetColors
import com.planetoto.customer_component.utils.disableRipple

/**
 * An editable search input field
 */
@ExperimentalAnimationApi
@Composable
fun PlanetSearchInput(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    placeholder: String? = null,
    helperText: String? = null,
    isError: Boolean = false,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Large,
    enabled: Boolean = true,
    hasClearAction: Boolean = true,
    colors: BaseTextFieldColors = BaseTextFieldColors(),
    onTextChange: (String) -> Unit,
    onSearchClicked: ((String) -> Unit)? = null,
    onActionDone: (KeyboardActionScope.() -> Unit)? = null
) {
    val iconTint = remember(enabled) {
        if (enabled) PlanetColors.Solid.content03 else PlanetColors.Solid.neutralBorder01
    }

    BaseTextField(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = false,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = onActionDone),
        singleLine = true,
        size = size,
        helperText = helperText,
        isError = isError,
        hasClearAction = hasClearAction,
        colors = colors,
        suffixBox = {
            Box(
                modifier = Modifier
                    .height(it)
                    .disableRipple { onSearchClicked?.invoke(text) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    tint = iconTint.color,
                    modifier = Modifier.padding(end = LocalPadding.current.small)
                )
            }
        }
    )
}

/**
 * An editable search input field
 */
@ExperimentalAnimationApi
@Composable
fun PlanetSearchInput(
    modifier: Modifier = Modifier,
    text: TextFieldValue,
    label: String? = null,
    placeholder: String? = null,
    helperText: String? = null,
    isError: Boolean = false,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Large,
    enabled: Boolean = true,
    hasClearAction: Boolean = true,
    colors: BaseTextFieldColors = BaseTextFieldColors(),
    onTextChange: (TextFieldValue) -> Unit,
    onSearchClicked: ((TextFieldValue) -> Unit)? = null,
    onActionDone: (KeyboardActionScope.() -> Unit)? = null
) {
    val iconTint = remember(enabled) {
        if (enabled) PlanetColors.Solid.content03 else PlanetColors.Solid.neutralBorder01
    }

    BaseTextField(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = false,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = onActionDone),
        singleLine = true,
        size = size,
        helperText = helperText,
        isError = isError,
        hasClearAction = hasClearAction,
        colors = colors,
        suffixBox = {
            Box(
                modifier = Modifier
                    .height(it)
                    .disableRipple { onSearchClicked?.invoke(text) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    tint = iconTint.color,
                    modifier = Modifier.padding(end = LocalPadding.current.small)
                )
            }
        }
    )
}

/**
 * Non-editable search input field
 */
@ExperimentalAnimationApi
@Composable
fun PlanetSearchInput(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    placeholder: String? = null,
    helperText: String? = null,
    isError: Boolean = false,
    size: PlanetTextFieldSize = PlanetTextFieldSize.Large,
    enabled: Boolean = true,
    hasClearAction: Boolean = false,
    colors: BaseTextFieldColors = BaseTextFieldColors(),
    onClearText: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    val iconTint = remember(enabled) {
        if (enabled) PlanetColors.Solid.content03 else PlanetColors.Solid.neutralBorder01
    }

    BaseTextField(
        modifier = modifier,
        text = text,
        onTextChange = { if (it.isEmpty()) onClearText?.invoke() },
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = true,
        singleLine = true,
        size = size,
        helperText = helperText,
        isError = isError,
        hasClearAction = hasClearAction,
        onClick = onClick,
        colors = colors,
        suffixBox = {
            Box(
                modifier = Modifier
                    .size(it)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    tint = iconTint.color,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onClick)
                )
            }
        }
    )
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun PreviewSearchInput() {
    var text by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        PlanetSearchInput(
            text = text,
            onTextChange = { text = it },
            onSearchClicked = {},
            placeholder = "Search",
            enabled = true
        )
        PlanetSearchInput(
            text = text,
            onTextChange = { text = it },
            onSearchClicked = {},
            placeholder = "Search",
            enabled = false
        )
    }
}