package pt.isel.ion.teams.authentication

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import pt.isel.ion.teams.common.Uris
import java.time.Duration
import java.util.*
import kotlin.math.log

const val GITHUB_OAUTH_URI = "https://github.com/login/oauth/authorize"
const val GITHUB_CLIENT_ID = "ed269215b2b47847a3a5"
const val GITHUB_TEACHER_SCOPE = "admin:org"

const val ONE_HOUR: Long = 60 * 60

@RestController
@RequestMapping
class AuthenticationController {

    @GetMapping(Uris.Login.PATH)
    fun getLogin(): ResponseEntity<Any> {
        val state = UUID.randomUUID().toString()

        val cookie = ResponseCookie.from("state", state)
            .path("/auth/callback")
            .domain("localhost")
            .maxAge(ONE_HOUR)
            .httpOnly(true)
            .secure(false)
            .sameSite("None")
            .build()

        return ResponseEntity
            .status(303)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .header(HttpHeaders.LOCATION, GITHUB_OAUTH_URI +
                    "?client_id=" + GITHUB_CLIENT_ID + "&" +
                    "scope=" + GITHUB_TEACHER_SCOPE + "&" +
                    "state=" + state
            )
            .build()
    }

    @GetMapping(Uris.Callback.PATH)
    fun getCallback(
        //@CookieValue state: String
    ) {
        
    }
}