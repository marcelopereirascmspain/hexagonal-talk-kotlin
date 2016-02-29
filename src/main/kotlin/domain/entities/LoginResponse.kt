package domain.entities

data class LoginResponse(val id: String, val name: String, val email: String)

fun fromUser(user: User): LoginResponse {
    return LoginResponse(user.id, user.name, user.email)
}