package usecases.auth

import repositories.AuthRepository

class VerifyCodeUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(phone: String, otp: String) = repository.verifyCode(phone = phone, otp = otp)
}