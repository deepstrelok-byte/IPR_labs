package usecases

import repositories.UserRepository
import usecases.user.ChangeVerificationUseCase
import usecases.user.FetchIsVerifiedUseCase
import usecases.user.FetchNameUseCase
import usecases.user.FetchOrganizationNameUseCase
import usecases.user.UpdateCurrentUserUseCase

class UserUseCases(
    repository: UserRepository
) {
    val fetchName = FetchNameUseCase(repository)

    val fetchIsVerified = FetchIsVerifiedUseCase(repository)
    val fetchOrganizationName = FetchOrganizationNameUseCase(repository)

    val updateCurrentUserInfo = UpdateCurrentUserUseCase(repository)

    val changeVerification = ChangeVerificationUseCase(repository)
}