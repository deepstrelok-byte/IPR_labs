import ktor.TokenProvider
import ktor.auth.AuthRemoteDataSource
import org.koin.dsl.module
import repositories.AuthRepository
import repositories.AuthRepositoryImpl
import settings.AuthLocalDataSource
import settings.TokenProviderImpl
import usecases.AuthUseCases
import usecases.extra.RegisterUseCase

internal val authModule = module {
    single<AuthRemoteDataSource> {
        AuthRemoteDataSource(
            get(),
            get()
        )
    }
    factory<AuthLocalDataSource> {
        AuthLocalDataSource(
            get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
    }



    factory<AuthUseCases> {
        AuthUseCases(get())
    }

    factory<RegisterUseCase> {
        RegisterUseCase(get(), get())
    }

    factory<TokenProvider> {
        TokenProviderImpl(get())
    }
}


