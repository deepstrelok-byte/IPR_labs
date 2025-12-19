package usecases.user

import repositories.UserRepository

class FetchOrganizationNameUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke() = repository.fetchOrganizationName()
}