package pt.isel.ion.teams.authentication

import ch.qos.logback.core.net.server.Client

/* ******************** TOKENS & COOKIES ******************** */
data class ClientToken(
    val access_token: String,
    val scope: String,
    val token_type: String
)

data class CompactClientToken(
    val access_token: String
)

data class SessionCookie(
    val sessionId: String
)

fun ClientToken.toCompact() = CompactClientToken(this.access_token)

/* ******************** USER REGISTRATION ******************** */

data class TeacherInfoDbWrite(
    val name: String,
    val number: Int,
    val email: String,
    var office: String
)

data class StudentInfoDbWrite(
    val name: String,
    val number: Int
)

data class UserInfoInputModel(
    val name: String,
    val number: Int,
    val email: String?,
    var office: String?
)