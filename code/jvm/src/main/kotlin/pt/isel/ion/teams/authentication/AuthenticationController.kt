package pt.isel.ion.teams.authentication

import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.InvalidAuthenticationStateException
import pt.isel.ion.teams.common.errors.InvalidClientId
import pt.isel.ion.teams.common.errors.NoAccessTokenException
import reactor.core.publisher.Mono
import java.util.*

const val GITHUB_OAUTH_URI = "https://github.com/login/oauth/authorize"
const val GITHUB_TEACHER_SCOPE = "admin:org"

const val HALF_HOUR: Long = 60 * 30
const val DESKTOP_CLIENT_ID = "desktop"
const val WEB_CLIENT_ID = "web"

@RestController
@RequestMapping
class AuthenticationController {

    @GetMapping(Uris.Login.PATH)
    fun getLogin(
        @RequestParam clientId: String
    ): ResponseEntity<Any> {
        val state = UUID.randomUUID().toString()

        val stateCookie = ResponseCookie.from("userState", state)
            .path("/auth/callback")
            .domain("localhost")
            .maxAge(HALF_HOUR)
            .httpOnly(true)
            .secure(false)
            .sameSite("Strict")
            .build()

        val clientId = ResponseCookie.from("clientId", clientId)
            .path("/auth/callback")
            .domain("localhost")
            .maxAge(HALF_HOUR)
            .httpOnly(true)
            .secure(false)
            .sameSite("Strict")
            .build()

        return ResponseEntity
            .status(303)
            .header(HttpHeaders.SET_COOKIE, stateCookie.toString())
            .header(HttpHeaders.SET_COOKIE, clientId.toString())
            .header(
                HttpHeaders.LOCATION, GITHUB_OAUTH_URI +
                        "?client_id=" + System.getenv("CLIENT_ID") + "&" +
                        "scope=" + GITHUB_TEACHER_SCOPE + "&" +
                        "state=" + state
            )
            .build()
    }

    @GetMapping(Uris.Callback.PATH)
    fun getCallback(
        @RequestParam code: String,
        @RequestParam state: String,
        @CookieValue userState: String,
        @CookieValue clientId: String
    ): ResponseEntity<Any> {
        //Verification of state to prevent CSRF attack
        if (!state.equals(userState)) throw InvalidAuthenticationStateException()

        //Verification of the type of client logging in, if the client is the desktop app the code is sent
        // and the desktop app is responsible for retrieving its Access token
        if (clientId == DESKTOP_CLIENT_ID) {
            return ResponseEntity
                .status(303)
                .header(
                    HttpHeaders.LOCATION, "ion-teams://code=" + code
                )
                .build()
        } else if (clientId == WEB_CLIENT_ID) {
            val accessToken = getAccessToken(code) ?: throw NoAccessTokenException()

            return ResponseEntity
                .ok(accessToken)
        }

        throw InvalidClientId()
    }

    @GetMapping(Uris.DesktopAccessToken.PATH)
    fun getDesktopAccessToken(
        @RequestParam code: String
    ): ResponseEntity<Any> {
        //TODO: should this verify a state?
        val accessToken = getAccessToken(code) ?: throw NoAccessTokenException()

        return ResponseEntity
            .ok(accessToken)
    }

    /**
     * Function used to fetch the access token associated to the given code from the github token endpoint
     * @param code The code from the authentication flow
     * @return client token and aditional information
     */
    fun getAccessToken(code: String): ClientToken? {
        val webClient = WebClient.create("https://github.com")
        val uri = "/login/oauth/access_token?client_id=" + System.getenv("CLIENT_ID") +
                "&client_secret=" + System.getenv("CLIENT_SECRET") +
                "&code=" + code

        val resp: Mono<ClientToken> =
            webClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ClientToken::class.java)

        return resp.block()
    }
}