
import ktor.ShareCareRemoteDataSource
import org.koin.dsl.module
import repositories.ShareCareRepository
import repositories.ShareCareRepositoryImpl
import usecases.ShareCareUseCases

internal val shareCareModule = module {
    single<ShareCareRemoteDataSource> {
        ShareCareRemoteDataSource(
            get(), get()
        )
    }

    single<ShareCareRepository> {
        ShareCareRepositoryImpl(get())
    }

    factory<ShareCareUseCases> {
        ShareCareUseCases(get())
    }
}