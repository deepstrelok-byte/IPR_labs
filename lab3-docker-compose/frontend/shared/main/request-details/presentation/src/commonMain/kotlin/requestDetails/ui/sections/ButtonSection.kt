package requestDetails.ui.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.FileUpload
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import animations.NetworkButtonIconAnimation
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import view.theme.colors.CustomColors

@Composable
internal fun ColumnScope.ButtonSection(
    isEditable: Boolean,
    isEditing: Boolean,
    requestText: String,
    category: ItemCategory?,
    deliveryTypes: List<DeliveryType>,
    initialText: String,
    initialDeliveryTypes: List<DeliveryType>,
    initialCategory: ItemCategory?,
    isLoading: Boolean,
    isDeleteLoading: Boolean,
    onDeleteClick: () -> Unit,
    createOrEditRequest: () -> Unit,
    onAcceptClick: () -> Unit
) {
    val animationSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessLow)

    val allFieldsFilled =
        (requestText.isNotBlank() && category != null && deliveryTypes.isNotEmpty())

    var isDeleting by remember { mutableStateOf(false) }

    AnimatedContent(isDeleting, transitionSpec = {
        fadeIn(animationSpec = animationSpec)
            .togetherWith(fadeOut(animationSpec = animationSpec))
    }) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            if (it) {
                Row(Modifier.padding(horizontal = Paddings.medium)) {
                    Button(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.errorContainer,
                            contentColor = colorScheme.onErrorContainer
                        )
                    ) {
                        NetworkButtonIconAnimation(
                            icon = Icons.Rounded.DeleteOutline,
                            isLoading = isDeleteLoading
                        )
                    }
                    SpacerH(Paddings.small)
                    Button(onClick = { isDeleting = false }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Rounded.Close, null)
                        SpacerH(Paddings.small)
                        Text("Отмена", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            } else {
                if (isEditable) {
                    Row(Modifier.padding(horizontal = Paddings.medium)) {
                        if (isEditing) {
                            Button(
                                onClick = { isDeleting = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorScheme.errorContainer,
                                    contentColor = colorScheme.onErrorContainer
                                )
                            ) {
                                Icon(Icons.Rounded.DeleteOutline, null)
                            }
                            SpacerH(Paddings.small)
                        }
                        Button(
                            onClick = createOrEditRequest,
                            enabled = allFieldsFilled &&
                                    (!isEditing || (
                                            (initialDeliveryTypes.toSet() != deliveryTypes.toSet()) ||
                                                    initialCategory != category ||
                                                    initialText != requestText
                                            )),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val text = if (isEditing) "Редактировать" else "Создать заявку"
                            NetworkButtonIconAnimation(
                                icon = if (isEditing) Icons.Rounded.Edit else Icons.Rounded.FileUpload,
                                isLoading = isLoading
                            )
                            SpacerH(Paddings.small)
                            Text(text, maxLines = 1, overflow = TextOverflow.Ellipsis)

                        }
                    }
                    SpacerV(Paddings.small)
                    AnimatedVisibility(!allFieldsFilled) {
                        Text(
                            "Небходимо заполнить все поля",
                            style = typography.bodyMedium,
                            color = colorScheme.onBackground.copy(alpha = .8f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    val isDark = isSystemInDarkTheme()
                    Button(
                        onClick = onAcceptClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) CustomColors.greenContainer else CustomColors.green,
                            contentColor = if (isDark) CustomColors.darkGreen else CustomColors.greenContainer
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        NetworkButtonIconAnimation(Icons.Rounded.Verified, isLoading)
                        SpacerH(Paddings.small)
                        Text("Откликнуться", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }


}