package usecases.auth

import repositories.AuthRepository

class FetchTokenUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.fetchToken()
}