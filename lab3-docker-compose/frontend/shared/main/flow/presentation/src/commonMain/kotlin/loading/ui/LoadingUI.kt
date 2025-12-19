package loading.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import loading.components.LoadingComponent
import network.NetworkState
import utils.SpacerV
import view.consts.Paddings

@Composable
fun LoadingUI(
    component: LoadingComponent
) {
    val textStyle = typography.headlineLarge

    val updateUserInfoResult by component.updateUserInfoResult.collectAsState()

    AnimatedVisibility(
        updateUserInfoResult !is NetworkState.Success,
        enter = fadeIn(tween(0)), // without animation
        exit = fadeOut()
    ) {
        Box(Modifier.fillMaxSize().background(colorScheme.background).clickable(indication = null, interactionSource = null) {}) {
            Text(
                text = component.helloText,
                modifier = Modifier.align(Alignment.Center)
                    .padding(horizontal = Paddings.listHorizontalPadding),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = textStyle,
                autoSize = TextAutoSize.StepBased(maxFontSize = textStyle.fontSize)
            )

            Crossfade(
                updateUserInfoResult,
                modifier = Modifier.animateContentSize().align(Alignment.BottomCenter)
            ) { result ->
                Box(
                    Modifier
                        .padding(bottom = Paddings.enormous * 3)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (result.isLoading() || result.isAFK()) {
                        LoadingIndicator()
                    } else if (result.isErrored()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Не удалось загрузить данные о пользователе",
                                modifier = Modifier.padding(horizontal = Paddings.listHorizontalPadding),
                                textAlign = TextAlign.Center
                            )
                            SpacerV(Paddings.small)
                            Button(onClick = { component.updateUserInfo() }) {
                                Text("Ещё раз")
                            }
                        }
                    }
                }
            }

        }
    }
}