package utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerV(h: Dp) = Spacer(modifier = Modifier.height(h))

@Composable
fun SpacerH(w: Dp) = Spacer(modifier = Modifier.width(w))