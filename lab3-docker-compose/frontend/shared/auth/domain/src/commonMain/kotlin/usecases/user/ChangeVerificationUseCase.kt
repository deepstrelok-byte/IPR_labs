package usecases.user

import repositories.UserRepository

class ChangeVerificationUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(isVerified: Boolean, organizationName: String?) =
        repository.changeVerification(isVerified = isVerified, organizationName = organizationName)
}