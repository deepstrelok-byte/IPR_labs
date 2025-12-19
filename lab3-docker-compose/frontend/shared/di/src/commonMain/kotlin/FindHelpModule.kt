
import ktor.FindHelpRemoteDataSource
import org.koin.dsl.module
import repositories.FindHelpRepository
import repositories.FindHelpRepositoryImpl
import usecases.FindHelpUseCases

internal val findHelpModule = module {
    single<FindHelpRemoteDataSource> {
        FindHelpRemoteDataSource(
            get(), get()
        )
    }

    single<FindHelpRepository> {
        FindHelpRepositoryImpl(get())
    }

    factory<FindHelpUseCases> {
        FindHelpUseCases(get())
    }
}