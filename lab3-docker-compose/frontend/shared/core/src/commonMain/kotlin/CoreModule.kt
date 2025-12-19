import ktor.ktorModule
import org.koin.core.module.Module
import org.koin.dsl.module
import settings.settingsModule

val coreModule: (enableLogging: Boolean) -> Module =
    { enableLogging ->
        module {
            includes(settingsModule, ktorModule(enableLogging))
        }
    }
