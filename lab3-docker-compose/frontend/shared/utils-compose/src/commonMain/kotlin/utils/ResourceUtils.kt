package utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


val StringResource.value
    @Composable
    get() = stringResource(this)