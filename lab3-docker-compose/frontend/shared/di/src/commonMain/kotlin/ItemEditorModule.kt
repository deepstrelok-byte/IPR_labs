import ktor.ItemEditorRemoteDataSource
import org.koin.dsl.module
import repositories.ItemEditorRepository
import repositories.ItemEditorRepositoryImpl
import usecases.ItemEditorUseCases

internal val itemEditorModule = module {
    single<ItemEditorRemoteDataSource> {
        ItemEditorRemoteDataSource(
            get(), get()
        )
    }

    single<ItemEditorRepository> {
        ItemEditorRepositoryImpl(get())
    }

    factory<ItemEditorUseCases> {
        ItemEditorUseCases(get())
    }
}
