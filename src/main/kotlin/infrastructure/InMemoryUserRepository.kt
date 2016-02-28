package infrastructure

import domain.entities.User
import domain.entities.UserRepository

class InMemoryUserRepository : UserRepository {
    override fun findOneByEmail(email: String): User? {
        return User("1", "john smith", "john.smith@mail.com", "a1b2c3")
    }
}