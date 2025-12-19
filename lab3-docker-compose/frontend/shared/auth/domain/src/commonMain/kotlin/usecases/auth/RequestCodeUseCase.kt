package usecases.auth

import repositories.AuthRepository

class RequestCodeUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(phone: String) = repository.requestCode(phone)
}