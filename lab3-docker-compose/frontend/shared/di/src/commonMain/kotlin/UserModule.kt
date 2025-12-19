
import ktor.user.UserRemoteDataSource
import org.koin.dsl.module
import repositories.UserRepository
import repositories.UserRepositoryImpl
import settings.UserLocalDataSource
import usecases.UserUseCases

internal val userModule = module {
    single<UserRemoteDataSource> {
        UserRemoteDataSource(
            get(), get()
        )
    }
    single<UserLocalDataSource> {
        UserLocalDataSource(
            get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }



    factory<UserUseCases> {
        UserUseCases(get())
    }
}
