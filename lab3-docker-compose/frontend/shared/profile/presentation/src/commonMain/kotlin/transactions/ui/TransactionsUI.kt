package transactions.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import common.ProfileHeader
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import entities.Transaction
import foundation.scrollables.BottomScrollEdgeFade
import foundation.scrollables.ScrollEdgeFade
import network.NetworkState
import transactions.components.TransactionsComponent
import utils.SpacerV
import view.consts.Paddings
import view.theme.colors.CustomColors
import widgets.glass.BackGlassButton
import widgets.sections.SectionTitle

@Composable
fun TransactionsUI(
    component: TransactionsComponent
) {
    val transactions by component.transactions.collectAsState()
    val transactionsInfo by component.transactionsInfo.collectAsState()

    val windowInsets = WindowInsets.safeContent
    val safeContentPaddings = windowInsets.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()

    val lazyListState = rememberLazyListState()


    val density = LocalDensity.current

    val topInsetsHeightDp = with(density) { windowInsets.getTop(this).toDp() }
    val bottomInsetsHeightDp = with(density) { windowInsets.getBottom(this).toDp() }

    val isScrollable = (lazyListState.canScrollForward || lazyListState.canScrollBackward)

    val hazeState = rememberHazeState()


    val items = transactions.data

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Box(
                Modifier.padding(
                    top = topPadding + Paddings.small,
                    start = Paddings.listHorizontalPadding
                )
            ) {
                BackGlassButton(hazeState = hazeState) {
                    component.pop()
                }
            }
        }
    ) { padding ->
        Box() {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .hazeSource(hazeState).padding(horizontal = Paddings.listHorizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyListState
            ) {
                item(key = "profileHeader") {
                    SpacerV(topInsetsHeightDp)
                    SpacerV(padding.calculateTopPadding() - topPadding)
                    ProfileHeader(
                        profileData = component.profileData
                    ) {}
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(transactionsInfo != null) {
                            val donated = transactionsInfo?.donated ?: 0
                            val received = transactionsInfo?.received ?: 0
                            Column {
                                SpacerV(Paddings.small)
                                Text(
                                    "Передано: $donated",
                                    color = CustomColors.green,
                                    style = typography.labelMedium,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic,
                                    modifier = Modifier.alpha(.7f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "Получено: $received", style = typography.labelMedium,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic,
                                    modifier = Modifier.alpha(.7f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    SpacerV(Paddings.semiSmall)
                    SectionTitle("Операции", 0.dp)
                }

                if (items != null) {
                    if (items.isNotEmpty()) {
                        items(items = items, key = { it.itemId }) { transaction ->
                            TransactionItem(transaction)
                        }
                    } else {
                        item("emptyList") {
                            Text("Тут пусто", modifier = Modifier.animateItem())
                        }
                    }


                    item("extraInfo") {
                        SpacerV(Paddings.small)
                        Column(
                            modifier = Modifier.animateItem(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Здесь отображаются только завершённые операции",
                                style = typography.labelMedium,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Получатель и даритель должны отметить, что `сделка` прошла успешно",
                                style = typography.labelMedium,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.alpha(.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {

                    if (transactions.isLoading()) {
                        item("loadingAnimation") {

                            LoadingIndicator(modifier = Modifier.animateItem())
                        }
                    } else if (transactions is NetworkState.Error) {
                        item("loadingError") {
                            val transactions =
                                (transactions as NetworkState.Error<List<Transaction>>)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.animateItem()
                            ) {
                                Text(
                                    transactions.prettyPrint,
                                    textAlign = TextAlign.Center,
                                )
                                Button(onClick = component::fetchTransactions) {
                                    Text("Ещё раз")
                                }
                            }
                        }
                    }
                }
                item("endSpacer") {
                    SpacerV(
                        (
                                if (bottomInsetsHeightDp >= Paddings.small) bottomInsetsHeightDp else Paddings.medium
                                ) + if (isScrollable) Paddings.endListPadding else 0.dp
                    )
                }

            }

            if (isScrollable) {
                ScrollEdgeFade(
                    modifier = Modifier.fillMaxWidth().align(Alignment.TopStart),
                    solidHeight = topInsetsHeightDp / 2,
                    shadowHeight = topInsetsHeightDp,
                    isVisible = lazyListState.canScrollBackward,
                )

                BottomScrollEdgeFade(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart),
                    solidHeight = bottomInsetsHeightDp,
                    isVisible = lazyListState.canScrollForward
                            && WindowInsets.ime.asPaddingValues().calculateBottomPadding() == 0.dp
                )
            }
        }
    }
}