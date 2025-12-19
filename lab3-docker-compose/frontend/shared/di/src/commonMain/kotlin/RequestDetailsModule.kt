import ktor.RequestDetailsRemoteDataSource
import org.koin.dsl.module
import repositories.RequestDetailsRepository
import repositories.RequestDetailsRepositoryImpl
import usecases.RequestDetailsUseCases


internal val requestDetailsModule = module {
    single<RequestDetailsRemoteDataSource> {
        RequestDetailsRemoteDataSource(
            get(), get()
        )
    }

    single<RequestDetailsRepository> {
        RequestDetailsRepositoryImpl(get())
    }

    factory<RequestDetailsUseCases> {
        RequestDetailsUseCases(get())
    }
}
