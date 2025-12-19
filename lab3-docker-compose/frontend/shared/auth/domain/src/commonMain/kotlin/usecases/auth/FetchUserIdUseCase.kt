package usecases.auth

import repositories.AuthRepository

class FetchUserIdUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.fetchUserId()
}