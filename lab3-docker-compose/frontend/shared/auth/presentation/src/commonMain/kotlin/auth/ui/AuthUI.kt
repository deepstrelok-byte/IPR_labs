package auth.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import auth.components.AuthComponent
import auth.components.AuthProgressState
import foundation.AsyncLocalImage
import foundation.scrollables.VerticalScrollableBox
import resources.RImages
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import view.consts.Sizes.logoMaxSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthUI(
    component: AuthComponent
) {

    val authProgressState by component.currentProgressState.collectAsState()

    val windowInsets = WindowInsets.safeContent.exclude(WindowInsets.ime)


    BackHandler(enabled = authProgressState == AuthProgressState.OTPCode) {
        component.onBackClick()
    }


    val verifyCodeResult by component.verifyCodeResult.collectAsState()

    Box(
        Modifier.imePadding().fillMaxSize().fastBackground(colorScheme.background),
    ) {
        VerticalScrollableBox(
            modifier = Modifier.fillMaxSize(),
            windowInsets = windowInsets,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.animateContentSize()
            ) {
                SpacerV(Paddings.medium)
                Card(
                    modifier = Modifier
                        .padding(horizontal = Paddings.medium)
                        .sizeIn(maxWidth = logoMaxSize, maxHeight = logoMaxSize)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = shapes.extraLarge
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        AsyncLocalImage(
                            RImages.ICON,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(.5f),
                            colorFilter = ColorFilter.tint(color = lerp(colorScheme.primary, colorScheme.onBackground, .5f))
                        )
                    }
                }
                SpacerV(Paddings.medium)
                Text(
                    "ДоброДар",
                    textAlign = TextAlign.Center,
                    style = typography.headlineLargeEmphasized,
                    fontWeight = FontWeight.Medium
                )
                SpacerV(Paddings.semiMedium)

                Crossfade(authProgressState) { progressState ->

                    Column(
                        Modifier
                            .sizeIn(maxWidth = logoMaxSize)
                            .padding(horizontal = Paddings.medium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (progressState) {
                            AuthProgressState.PHONE -> {
                                PhoneProgressStateUI(component)
                            }

                            AuthProgressState.OTPCode -> {
                                OTPCodeProgressStateUI(
                                    OTPCode = component.OTPCode,
                                    isLoading = verifyCodeResult.isLoading(),
                                    onNextClick = { component.onVerifyCodeClick() },
                                    onBackClick = { component.onBackClick() }
                                )
                            }
                        }
                    }
                }

            }

        }
    }
}