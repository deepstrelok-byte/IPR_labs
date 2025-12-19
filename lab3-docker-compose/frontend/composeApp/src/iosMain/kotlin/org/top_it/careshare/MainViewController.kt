package org.top_it.careshare

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import components.RealRootComponent
import components.RootComponent
import compose.RootUI
import initKoin

fun MainViewController() = ComposeUIViewController {
    Box() {
        initKoin(
            enableLogging = true
        ) {}

        val rootComponent: RootComponent = RealRootComponent(
            DefaultComponentContext(
                ApplicationLifecycle()
            )
        )

        RootUI(rootComponent)
    }
}