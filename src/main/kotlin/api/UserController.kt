package api

import domain.entities.*
import infrastructure.InMemoryUserRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

class CoolEncoder : PasswordEncoder {
    override fun isPasswordValid(hashedPassword: String, password: String): Boolean {
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