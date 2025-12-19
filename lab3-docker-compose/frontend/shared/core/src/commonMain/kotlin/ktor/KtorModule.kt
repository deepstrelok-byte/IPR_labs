package ktor

import org.koin.core.module.Module
import org.koin.dsl.module

internal val ktorModule: (enableLogging: Boolean) -> Module =
    { enableLogging ->
        module {
            single { createHttpClient(enableLogging) }
        }
    }