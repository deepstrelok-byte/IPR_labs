package usecases.extra

import kotlinx.coroutines.flow.onCompletion
import repositories.AuthRepository
import repositories.UserRepository

// More related to Auth!
class RegisterUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(name: String, telegram: String) = authRepository.register(
        name = name, telegram = telegram
    ).onCompletion {
        userRepository.saveName(name)
    }
}