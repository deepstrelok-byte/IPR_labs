package usecases.user

import repositories.UserRepository

class UpdateCurrentUserUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke() = repository.updateUserInfo()
}