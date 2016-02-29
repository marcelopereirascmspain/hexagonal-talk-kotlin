package domain.entities

class LoginUseCase(val session: Session, val repository: UserRepository, val encoder: PasswordEncoder) {
    fun execute(request: LoginRequest): LoginResponse {

        if (session.isUserLogged(request.email)) {
            throw UserAlreadyLoggedException("The user is already logged");
        }

        val email = request.email.toLowerCase();
        val user = repository.findOneByEmail(email)

        if (user === null) {
            throw UserNotFoundException("Can't find user with email: ${email}")
        }

        if (!encoder.isPasswordValid(user.passwordHash, request.password)) {
            throw IncorrectPasswordException("The password is incorrect")
        }

        return fromUser(user)
    }
}

interface PasswordEncoder {
    fun isPasswordValid(hashedPassword: String, password: String): Boolean
}

interface Session {
    fun isUserLogged(email: String): Boolean
}