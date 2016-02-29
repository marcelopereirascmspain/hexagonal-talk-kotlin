package api

import domain.entities.*
import infrastructure.InMemoryUserRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom

class CoolEncoder : PasswordEncoder {
    override fun isPasswordValid(hashedPassword: String, password: String): Boolean {
        val md = MessageDigest.getInstance("SHA-256")
        val salt = ByteArray(32)
        val sr = SecureRandom.getInstance("SHA1PRNG")
        sr.nextBytes(salt)

        val saltedPassword = String(salt, StandardCharsets.UTF_8) + password
        val digested = md.digest(saltedPassword.toByteArray())

        println(String(digested, StandardCharsets.UTF_8))

        return true
    }
}

class CoolSession : Session {
    override fun isUserLogged(email: String): Boolean {
        return false
    }
}

@RestController
class GreetingController {

    @RequestMapping("/login")
    fun login(
            @RequestParam(value = "email") email: String,
            @RequestParam(value = "password") password: String): LoginResponse {

        val loginUseCase = LoginUseCase(CoolSession(), InMemoryUserRepository(), CoolEncoder());
        val request = LoginRequest(email.toLowerCase(), password)
        val response = loginUseCase.execute(request)

        return response;
    }
}