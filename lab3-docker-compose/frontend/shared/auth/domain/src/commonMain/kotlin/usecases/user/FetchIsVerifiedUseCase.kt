package usecases.user

import repositories.UserRepository

class FetchIsVerifiedUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke() = repository.fetchIsVerified()
}