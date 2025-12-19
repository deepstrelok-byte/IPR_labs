import local.SettingsLocalDataSource
import org.koin.dsl.module
import repositories.SettingsRepository
import repositories.SettingsRepositoryImpl
import usecases.SettingsUseCases


internal val settingsModule = module {
    single<SettingsLocalDataSource> {
        SettingsLocalDataSource(
            get()
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    factory<SettingsUseCases> {
        SettingsUseCases(get())
    }
}