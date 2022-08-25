package pt.isel.ion.teams.common

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.ion.teams.authentication.AuthenticationService
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Authentication interceptor.
 * Verifies the session for all applications trying to access the API.
 */
@Component
class AuthInterceptor(val service: AuthenticationService) : HandlerInterceptor {
    @Throws(ServletException::class, NullPointerException::class)
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        try {
            if ()
            val cookie = request.cookies.find { it.name == "session" } ?: return false
            service.getUserFromSession(cookie.value)
        }catch (e: NullPointerException){
            return false
        }
        return true
    }
}
