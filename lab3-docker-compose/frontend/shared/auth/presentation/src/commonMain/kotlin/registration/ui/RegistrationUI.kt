package registration.ui

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
import androidx.compose.foundation.text.input.insert
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NavigateNext
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import animations.NetworkButtonIconAnimation
import foundation.AsyncLocalImage
import foundation.scrollables.VerticalScrollableBox
import icons.Telegram
import registration.components.RegistrationComponent
import resources.RImages
import utils.SpacerH
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import view.consts.Sizes.logoMaxSize
import widgets.textField.SurfaceTextField

@Composable
fun RegistrationUI(component: RegistrationComponent) {


    val windowInsets = WindowInsets.safeContent.exclude(WindowInsets.ime)

    val registrationResult by component.registrationResult.collectAsState()

    VerticalScrollableBox(
        modifier = Modifier.fillMaxSize().fastBackground(colorScheme.background).imePadding(),
        windowInsets = windowInsets,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = Paddings.medium)
                .sizeIn(maxWidth = logoMaxSize)
        ) {
            Card(
                modifier = Modifier
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
                "Регистрация",
                textAlign = TextAlign.Center,
                style = typography.headlineLargeEmphasized,
                fontWeight = FontWeight.Medium
            )
            SpacerV(Paddings.semiMedium)


            SurfaceTextField(
                state = component.name,
                placeholderText = "Ваше имя",
                icon = Icons.Rounded.Person,
                singleLine = true,
                imeAction = ImeAction.Next,
                inputTransformation = {
                    val text = this.asCharSequence()

                    if (text.any { !it.isLetter() && it != ' ' } || text.length > 20) revertAllChanges()
                    else if (text.isNotEmpty()) {
                        val firstChar = text.first()
                        if (firstChar == ' ') revertAllChanges()
                        else replace(0, 1, firstChar.uppercase())
                    }
                }
            )
            SpacerV(Paddings.small)


            SurfaceTextField(
                state = component.telegram,
                placeholderText = "Телеграм @username",
                icon = Icons.Rounded.Telegram,
                singleLine = true,
                inputTransformation = {
                    val text = this.asCharSequence()

                    if (text.isNotEmpty()) {
                        if (text.first() != '@') {
                            this.insert(0, "@")
                        }

                        if (text.length > 1) {
                            val usernamePart = text.substring(1).lowercase()
                            val regex = Regex("^[a-z][a-z0-9_]{0,31}$")
                            if (!usernamePart.matches(regex)) {
                                revertAllChanges()
                            }
                        }
                    }

                }
            )
            SpacerV(Paddings.small)

            Text(
                "Он будет использован для связи",
                style = typography.bodyMedium,
                color = colorScheme.onBackground.copy(alpha = .8f),
                textAlign = TextAlign.Center
            )

            SpacerV(Paddings.big)
            Button(
                enabled = component.name.text.isNotEmpty() && component.telegram.text.length >= 5 + 1, //at least 5 characters + @
                onClick = {
                    component.onRegistrationClick()
                }
            ) {
                Text("Зарегистрироваться")
                SpacerH(Paddings.semiSmall)
                NetworkButtonIconAnimation(
                    icon = Icons.AutoMirrored.Rounded.NavigateNext,
                    isLoading = registrationResult.isLoading()
                )
            }
        }
    }
}