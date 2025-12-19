package usecases.auth

import repositories.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.logout()
}