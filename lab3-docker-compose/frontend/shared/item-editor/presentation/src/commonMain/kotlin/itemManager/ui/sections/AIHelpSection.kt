package itemManager.ui.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import animations.NetworkButtonIconAnimation
import entities.AICreateHelp
import logic.enums.ItemCategory
import network.NetworkState
import utils.SpacerH
import utils.SpacerV
import utils.title
import view.consts.Paddings
import view.theme.colors.CustomColors
import widgets.sections.SectionTitle

@Composable
fun AIHelpSection(
    isShown: Boolean,
    aiAnswer: NetworkState<AICreateHelp>,
    curTitle: String,
    curDescription: String,
    curCategory: ItemCategory?,
    onHelpClick: () -> Unit,
    onAcceptTitleClick: (String) -> Unit,
    onAcceptDescriptionClick: (String) -> Unit,
    onAcceptItemCategoryClick: (ItemCategory) -> Unit,
) {

    var isAiAnswerExpanded by remember { mutableStateOf(false) }

    Column {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column {
                AnimatedVisibility(isShown) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextButton(
                            onClick = {
                                onHelpClick()
                                isAiAnswerExpanded = true
                            },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = colorScheme.primaryContainer.copy(
                                    alpha = .2f
                                )
                            )
                        ) {
                            NetworkButtonIconAnimation(
                                icon = Icons.Rounded.AutoAwesome,
                                isLoading = aiAnswer.isLoading()
                            )
                            SpacerH(Paddings.small)
                            Text("Помощь от ИИ")
                        }
                        SpacerV(Paddings.semiSmall)
                        AnimatedVisibility(aiAnswer.data == null) {
                            Text(
                                "ИИ сгенерирует описание предмета",
                                style = typography.bodySmall,
                                color = colorScheme.onBackground.copy(alpha = .7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Column {
                AnimatedVisibility(!isShown) {
                    SpacerV(Paddings.small)
                    Text(
                        "Добавьте фотографию, чтобы увидеть some magic ✨",
                        style = typography.bodySmall,
                        color = colorScheme.onBackground.copy(alpha = .7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = Paddings.listHorizontalPadding)
                    )
                }
            }
        }

        AnimatedVisibility(aiAnswer is NetworkState.Success) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = Paddings.listHorizontalPadding).clip(shapes.medium).clickable {
                    isAiAnswerExpanded = !isAiAnswerExpanded
                },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionTitle(
                    "Вот, что предложил ИИ",
                    horizontalPadding = 0.dp,
                    modifier = Modifier.weight(1f, false)
                )
                Icon(
                    if (!isAiAnswerExpanded) Icons.Rounded.ExpandMore else Icons.Rounded.ExpandLess,
                    null
                )
            }
        }

        AnimatedVisibility(aiAnswer is NetworkState.Success && isAiAnswerExpanded) {
            val answer = aiAnswer.data
            Column(Modifier.padding(horizontal = Paddings.listHorizontalPadding / 2)) {
                SpacerV(Paddings.semiMedium)
                Text(
                    "Выберите нажатием, что хотите использовать",
                    style = typography.bodySmall,
                    color = colorScheme.onBackground.copy(alpha = .7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerV(Paddings.small)
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp + Paddings.small))
                        .background(colorScheme.surfaceContainerLow)
                        .padding(
                            horizontal = Paddings.listHorizontalPadding / 2
                        )
                        .padding(bottom = Paddings.semiMedium)
                ) {
                    val title = answer?.title ?: ""
                    val description = answer?.description ?: ""
                    val category = answer?.category ?: ItemCategory.Other
                    AIRecommendation(
                        type = "Название",
                        recommendation = title,
                        isApplied = curTitle == title
                    ) { onAcceptTitleClick(title) }
                    AIRecommendation(
                        type = "Описание",
                        recommendation = description,
                        isApplied = curDescription == description
                    ) { onAcceptDescriptionClick(description) }
                    AIRecommendation(
                        type = "Категория",
                        recommendation = category.title,
                        isApplied = curCategory == category
                    ) { onAcceptItemCategoryClick(category) }
                }
            }

        }
    }
}

@Composable
private fun AIRecommendation(
    type: String,
    recommendation: String,
    isApplied: Boolean,
    onClick: () -> Unit
) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                SpacerV(Paddings.small) //do not resize cuz of icon
                Text(
                    type,
                    style = typography.labelMedium,
                    modifier = Modifier.padding(
                        start = Paddings.small,
                        bottom = Paddings.ultraUltraSmall
                    )
                )
            }
            SpacerH(Paddings.semiSmall)
            AnimatedVisibility(
                isApplied,
                modifier = Modifier.offset(y = Paddings.ultraUltraSmall)
            ) {
                Icon(
                    Icons.Rounded.Done,
                    null,
                    tint = if (isSystemInDarkTheme()) CustomColors.green else CustomColors.darkGreen
                )
            }
        }
        Text(
            text = recommendation,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    onClick()
                }
                .background(colorScheme.surfaceContainerHigh)
                .padding(Paddings.small)
                .fillMaxWidth()
        )
    }
}