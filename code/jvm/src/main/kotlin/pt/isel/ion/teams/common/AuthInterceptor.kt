package pt.isel.ion.teams.common

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.ion.teams.authentication.AuthenticationService
import pt.isel.ion.teams.common.errors.UserNotAuthenticatedException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
//import org.springframework.mock.web.MockHttpServletRequest


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
            //If request is appart of a test let it through
            if (request is MockHttpServletRequest) return true

            //If request is a preflight let it through
            if (request.method == "OPTIONS") return true

            val cookie = request.cookies.find { it.name == "session" }
            cookie ?: throw UserNotAuthenticatedException()
            service.getUserFromSession(cookie.value)

        } catch (e: NullPointerException) {
            throw UserNotAuthenticatedException()
        }
        return true
    }
}
