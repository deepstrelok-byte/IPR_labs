import ktor.TransactionsRemoteDataSource
import org.koin.dsl.module
import repositories.TransactionsRepository
import repositories.TransactionsRepositoryImpl
import usecases.TransactionsUseCases


internal val profileModule = module {
    single<TransactionsRemoteDataSource> {
        TransactionsRemoteDataSource(
            get(), get()
        )
    }

    single<TransactionsRepository> {
        TransactionsRepositoryImpl(get())
    }



    factory<TransactionsUseCases> {
        TransactionsUseCases(get())
    }
}
