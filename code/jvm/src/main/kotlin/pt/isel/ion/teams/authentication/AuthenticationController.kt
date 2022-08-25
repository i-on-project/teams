package pt.isel.ion.teams.authentication

import io.netty.resolver.DefaultAddressResolverGroup
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.*
import pt.isel.ion.teams.students.StudentDbUpdate
import pt.isel.ion.teams.students.StudentsService
import pt.isel.ion.teams.teacher.TeacherDbUpdate
import pt.isel.ion.teams.teacher.TeachersService
import reactor.core.publisher.Mono
import java.net.URI
import java.net.URI.create
import java.net.http.HttpClient
import java.util.*
import javax.servlet.http.HttpServletResponse


const val GITHUB_OAUTH_URI = "https://github.com/login/oauth/authorize"
const val GITHUB_TEACHER_SCOPE = "admin:org"
const val GITHUB_STUDENT_SCOPE = "read:project"

const val HALF_HOUR: Long = 60 * 30
const val ONE_MONTH: Long = 60 * 60 * 24 * 30
const val DESKTOP_CLIENT_ID = "desktop"
const val DESKTOP_REGISTER_CLIENT_ID = "desktop-register"
const val WEB_CLIENT_ID = "web"
const val WEB_REGISTER_CLIENT_ID = "web-register"
const val TEACHER_SESSION_USER_TYPE = 'T'
const val STUDENT_SESSION_USER_TYPE = 'S'
//TODO: After deployment activate flag secure on cookies

@RestController
@RequestMapping
class AuthenticationController(
    val authService: AuthenticationService,
    val emailService: EmailService,
    val teachersService: TeachersService,
    val studentsService: StudentsService
) {

    /**
     * Endpoint used to start the login procedure.
     */
    @GetMapping(Uris.Login.PATH)
    fun getLogin(
        @RequestParam clientId: String
    ): ResponseEntity<Any> {
        return githubAuthRedirect(clientId)
    }

    /**
     * Used by the  Desktop app to follow the GitHub flow, if the external sign-in process is successful
     * the register process will be completed with a POST from the app directly to the service.
     */
    @GetMapping(Uris.Register.PATH)
    fun getRegisterUser(
        @RequestParam clientId: String,
    ): ResponseEntity<Any> {
        return githubAuthRedirect(clientId)
    }

    /**
     * Register endpoint adds a teacher or a student to the database, registering them as a user to the service.
     * If the user is a teacher a verification is made to ensure that the email provided is present in the list of
     * previously authorised teacher emails (maintained by the system admin).
     */
    @PostMapping(Uris.Register.PATH)
    fun postRegisterUser(
        @RequestParam clientId: String,
        @RequestBody userInfo: UserInfoInputModel,
        response: HttpServletResponse
    ): ResponseEntity<Any> {

        if (clientId == DESKTOP_REGISTER_CLIENT_ID) {

            if (userInfo.office == null || userInfo.email == null) throw MissingRegisterParametersException()
            if (!authService.checkIsAuthorisedTeacher(userInfo.email)) throw NotAnAuthorizedEmailException()

            teachersService.createTeacher(userInfo.toTeacherDbWrite())

            return ResponseEntity.status(201).build()
        } else if (clientId == WEB_REGISTER_CLIENT_ID) {
            studentsService.createStudent(userInfo.toStudentDbWrite())

            return githubAuthRedirect(clientId, userInfo.number.toString())
        }

        throw InvalidClientIdException()
    }

    /**
     * Callback endpoint used when the OAuth flow returns to the service after the user authenticated themselves
     * on GitHub.
     */
    @GetMapping(Uris.Callback.PATH)
    fun getCallback(
        @RequestParam code: String,
        @RequestParam state: String,
        @CookieValue userState: String,
        @CookieValue clientId: String
    ): ResponseEntity<Any> {
        //Verification of state to prevent CSRF attack
        if (state != userState) throw InvalidAuthenticationStateException()

        //TODO: create session

        var number: String? = null
        var processedClientId = clientId
        if (clientId.contains("+")) {
            val split = clientId.split("+")

            processedClientId = split[0]
            number = split[1]
        }

        //Verification of the type of client logging in, if the client is the desktop app the code is sent
        // and the desktop app is responsible for retrieving its Access token
        when (processedClientId) {
            DESKTOP_CLIENT_ID -> {
                return ResponseEntity
                    .status(303)
                    .header(
                        HttpHeaders.LOCATION, "ion-teams://code=$code&type=login"
                    )
                    .build()
            }

            WEB_CLIENT_ID -> {
                val accessToken = getAccessToken(code) ?: throw NoAccessTokenException()
                val ghUserInfo = getGithubUserInfo(accessToken.access_token) ?: throw NoGithubUserFoundException()

                //Verification if the user trying to log in is in fact registered
                try {
                    val user = studentsService.getStudentByUsername(ghUserInfo.login)
                    var verId = ""

                    try {
                        verId = authService.getVerificationId(user.number)
                    } catch (_: EmptyDbReturnException) {
                        //No verification id expected if user verified
                    }

                    if (!verId.equals("")) {
                        emailService.sendVerificationEmail(user.name, "a${user.number}@alunos.isel.pt", verId)

                        throw UserNotVerifiedException()
                    }
                } catch (e: EmptyDbReturnException) {
                    throw UserNotRegisteredException()
                }

                return ResponseEntity
                    .ok(accessToken)
            }

            DESKTOP_REGISTER_CLIENT_ID -> {
                return ResponseEntity
                    .status(303)
                    .header(
                        HttpHeaders.LOCATION, "ion-teams://code=$code&type=register"
                    )
                    .build()
            }

            WEB_REGISTER_CLIENT_ID -> {
                val accessToken = getAccessToken(code) ?: throw NoAccessTokenException()
                val ghUserInfo = getGithubUserInfo(accessToken.access_token) ?: throw NoGithubUserFoundException()

                if (number == null) throw InvalidClientIdException()
                studentsService.updateStudentUsername(
                    StudentDbUpdate(
                        number.toInt(),
                        githubusername = ghUserInfo.login
                    )
                )

                //Verification
                val user = studentsService.getStudentByUsername(ghUserInfo.login)

                //Creating and storing verification id
                val verificationId = UUID.randomUUID().toString()
                authService.createVerification(verificationId, user.number)

                //Sending verification email
                emailService.sendVerificationEmail(user.name, "a${user.number}@alunos.isel.pt", verificationId)

                return ResponseEntity
                    .status(303)
                    .header(
                        HttpHeaders.LOCATION, "http://localhost:3000/#/login?toVerify=true"
                    )
                    .build()
            }

            else -> throw InvalidClientIdException()
        }
    }

    /**
     * Endpoint used by the desktop application to retrieve the GitHub access token, this way the token which is
     * highly sensitive is never stored remotely on the service.
     */
    @GetMapping(Uris.DesktopAccessToken.PATH)
    fun getDesktopAccessToken(
        @RequestParam code: String?,
        @RequestParam type: String?,
        @RequestParam number: String?,
        @CookieValue accessToken: String?
    ): ResponseEntity<Any> {

        /**
         * If the request has an access token return that access token, if not fetch a new access token
         * from the GitHub token endpoint
         */
        if (accessToken != null && accessToken != "deleted") {
            return ResponseEntity
                .ok(AccessToken(accessToken))
        } else if(code != null && type != null) {

            val at = getAccessToken(code) ?: throw NoAccessTokenException()
            val ghUserInfo = getGithubUserInfo(at.access_token) ?: throw NoGithubUserFoundException()

            if (type == "register" && number != null) {
                //Associate GitHub username with school number
                teachersService.updateTeacher(TeacherDbUpdate(number.toInt(), githubusername = ghUserInfo.login))

                //Verification
                val user = teachersService.getTeacherByUsername(ghUserInfo.login)

                //Creating and storing verification id
                val verificationId = UUID.randomUUID().toString()
                authService.createVerification(verificationId, user.number)

                //Sending verification email
                emailService.sendVerificationEmail(user.name, user.email, verificationId)

                return ResponseEntity
                    .status(200)
                    .build()
            } else {
                //Verification if the user trying to log in is in fact registered
                try {
                    val teacher = teachersService.getTeacherByUsername(ghUserInfo.login)
                    var verId = ""

                    try {
                        verId = authService.getVerificationId(teacher.number)
                    } catch (_: EmptyDbReturnException) {
                        //No verification id expected if user verified
                    }

                    if (!verId.equals("")) {
                        emailService.sendVerificationEmail(teacher.name, teacher.email, verId)

                        throw UserNotVerifiedException()
                    }

                } catch (e: EmptyDbReturnException) {
                    throw UserNotRegisteredException()
                }

                //Session creation

                val user = teachersService.getTeacherByUsername(ghUserInfo.login)
                val sessionId = UUID.randomUUID().toString()
                authService.createSession(user.number, sessionId, TEACHER_SESSION_USER_TYPE)

                val accessTokenCookie = ResponseCookie.from("accessToken", at.access_token)
                    .path("/auth/")
                    .domain("localhost")
                    .maxAge(ONE_MONTH)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .build()

                val sessionCookie = ResponseCookie.from("session", sessionId)
                    .path("/")
                    .domain("localhost")
                    .maxAge(ONE_MONTH)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .build()

                return ResponseEntity
                    .status(200)
                    .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                    .body(AccessToken(at.access_token))
            }
        } else {
            return ResponseEntity
                .status(404)
                .build()
        }
    }

    /**
     * Used for verifying a new user, after the registration the users receive an email for account verification
     * with a link to this endpoint.
     *
     * The user will then be identified and verified.
     */
    @GetMapping(Uris.Verify.PATH)
    fun getVerify(
        @PathVariable code: String
    ): ResponseEntity<Any> {
        authService.verifyUser(code)

        return ResponseEntity
            .ok("Verification Successful.")
    }

    /**
     * Logout endpoint terminated the user session and removes its cookies.
     */
    @GetMapping(Uris.Logout.PATH)
    fun getLogout(
        @CookieValue session: String,
        @CookieValue accessToken: String?
    ): ResponseEntity<Any> {
        authService.deleteSession(session)

        if (accessToken != null) {
            val accessTokenCookie = ResponseCookie.from("accessToken", "deleted")
                .path("/auth/")
                .domain("localhost")
                .maxAge(HALF_HOUR)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build()

            val sessionCookie = ResponseCookie.from("session", "deleted")
                .path("/")
                .domain("localhost")
                .maxAge(HALF_HOUR)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build()

            return ResponseEntity
                .status(200)
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .build()
        }

        //TODO: Logout for web application
        return ResponseEntity
            .ok(null)
    }

    /* ****************** AUXILIARY FUNCTIONS ****************** */

    /**
     * Function responsible for creating the cookies that are necessary for an OAuth2.0 authentication, namely
     * the state and a clientId to identify the type of client requesting the authentication.
     *
     * The function then returns a ResponseEntity with status 303 for redirection to GitHub to complete the
     * authentication before returning to the server callback endpoint.
     */
    private fun githubAuthRedirect(clientId: String, number: String? = null): ResponseEntity<Any> {
        val state = UUID.randomUUID().toString()

        val stateCookie = ResponseCookie.from("userState", state)
            .path("/auth/callback")
            .domain("localhost")
            .maxAge(HALF_HOUR)
            .httpOnly(true)
            .secure(false)
            .sameSite("Lax")
            .build()

        //In student registration it is necessary to store the student number so that the GitHub username
        //can later be associated with that number.
        val clientIdCookieValue =
            if (number != null && clientId == WEB_REGISTER_CLIENT_ID) "$clientId+$number" else clientId

        val clientIdCookie = ResponseCookie.from("clientId", clientIdCookieValue)
            .path("/auth/callback")
            .domain("localhost")
            .maxAge(HALF_HOUR)
            .httpOnly(true)
            .secure(false)
            .sameSite("Lax")
            .build()

        if (clientId.contains("web")) {
            return ResponseEntity
                .status(200)
                .header(HttpHeaders.SET_COOKIE, stateCookie.toString())
                .header(HttpHeaders.SET_COOKIE, clientIdCookie.toString())
                .body(
                    JSONRedirectObj(
                        GITHUB_OAUTH_URI +
                                "?client_id=" + System.getenv("CLIENT_ID") + "&" +
                                "scope=" + GITHUB_STUDENT_SCOPE + "&" +
                                "state=" + state
                    )
                )
        } else {
            return ResponseEntity
                .status(303)
                .header(HttpHeaders.SET_COOKIE, stateCookie.toString())
                .header(HttpHeaders.SET_COOKIE, clientIdCookie.toString())
                .location(
                    URI.create(
                        GITHUB_OAUTH_URI +
                                "?client_id=" + System.getenv("CLIENT_ID") + "&" +
                                "scope=" + GITHUB_TEACHER_SCOPE + "&" +
                                "state=" + state
                    )
                )
                .build()
        }


    }


    /* ****************** HTTP REQUESTS ****************** */

    /**
     * Function used to fetch the access token associated to the given code from the GitHub token endpoint
     * @param code The code from the authentication flow
     * @return client token and additional information
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

    /**
     * Function used to fetch the user information of the user trying to log in
     */
    fun getGithubUserInfo(accessToken: String): GitHubUserInfo? {
        val webClient = WebClient.create("https://api.github.com")
        val uri = "/user"

        val resp: Mono<GitHubUserInfo> =
            webClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .bodyToMono(GitHubUserInfo::class.java)

        return resp.block()
    }
}