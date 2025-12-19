package ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import careshare.shared.hello.presentation.generated.resources.Res
import careshare.shared.hello.presentation.generated.resources.hello_desc
import careshare.shared.hello.presentation.generated.resources.hello_desc_title
import careshare.shared.hello.presentation.generated.resources.hello_title
import careshare.shared.hello.presentation.generated.resources.join_button
import components.FakeHelloComponent
import components.HelloComponent
import components.HelloComponent.Output
import foundation.AsyncLocalImage
import foundation.scrollables.VerticalScrollableBox
import org.jetbrains.compose.ui.tooling.preview.Preview
import resources.RImages
import utils.SpacerH
import utils.SpacerV
import utils.value
import view.consts.Paddings
import view.consts.Sizes.logoMaxSize


@Composable
fun HelloUI(
    component: HelloComponent
) {
    val num = component.num.collectAsState()


    val windowInsets = WindowInsets.safeContent
    val density = LocalDensity.current

    val descriptionMaxSize = remember { 500.dp }

    VerticalScrollableBox(
        modifier = Modifier.fillMaxSize(),
        windowInsets = windowInsets,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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
                text = Res.string.hello_title.value,
                textAlign = TextAlign.Center,
                autoSize = TextAutoSize.StepBased(maxFontSize = typography.headlineSmallEmphasized.fontSize),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = Paddings.large)
            )

            SpacerV(Paddings.medium)



            Card(
                modifier = Modifier
                    .widthIn(max = descriptionMaxSize)
                    .padding(horizontal = Paddings.small),
                shape = shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(Paddings.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = Res.string.hello_desc_title.value,
                        textAlign = TextAlign.Center,
                        style = typography.titleMedium,
                        autoSize = TextAutoSize.StepBased(maxFontSize = typography.titleMediumEmphasized.fontSize)
                    )
                    SpacerV(Paddings.small)

                    Text(
                        text = Res.string.hello_desc.value,
                        style = typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }



            SpacerV(Paddings.medium)

            Button(
                onClick = {
                    component.output(Output.NavigateToAuth)
                }
            ) {
                Icon(Icons.Rounded.FavoriteBorder, null)
                SpacerH(Paddings.semiSmall)
                Text(Res.string.join_button.value)

            }
            SpacerV(Paddings.medium)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HelloUIPreview() {
    HelloUI(FakeHelloComponent)
}