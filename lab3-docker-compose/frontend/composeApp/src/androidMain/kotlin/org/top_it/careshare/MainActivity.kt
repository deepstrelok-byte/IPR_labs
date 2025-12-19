package org.top_it.careshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.retainedComponent
import components.RealRootComponent
import components.RootComponent
import compose.RootUI
import foundation.scrollables.overscrollEffect.rememberCupertinoOverscrollFactory
import initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initKoin(
            enableLogging = true
        ) {
            androidContext(applicationContext)
        }

        val rootComponent: RootComponent = retainedComponent { componentContext ->
            RealRootComponent(componentContext)
        }
        screenSetup()

        setContent {
            CompositionLocalProvider(
                LocalOverscrollFactory provides rememberCupertinoOverscrollFactory()
            ) {
                RootUI(rootComponent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}