package domain.entities

interface UserRepository {
    fun findOneByEmail(email: String): User?
}