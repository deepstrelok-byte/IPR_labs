package usecases.user

import repositories.UserRepository

class FetchNameUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke() = repository.fetchName()
}