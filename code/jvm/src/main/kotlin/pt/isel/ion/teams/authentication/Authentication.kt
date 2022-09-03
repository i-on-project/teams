package pt.isel.ion.teams.authentication

import pt.isel.ion.teams.students.StudentDbWrite
import pt.isel.ion.teams.teacher.TeacherDbWrite

/**
 * This file contains the data class definitions for the all the types needed for authentication.
 */

/* ******************** TOKENS & COOKIES ******************** */
data class ClientToken(
    val access_token: String,
    val scope: String,
    val token_type: String
)

data class AccessToken(
    val access_token: String,
)

data class CompactClientToken(
    val access_token: String
)

data class JSONRedirectObj(
    val location: String
)

data class GitHubUserInfo(
    val login: String,
    val url: String,
    val name: String
)

/* ******************** USER REGISTRATION ******************** */

data class UserInfoInputModel(
    val number: Int,
    val name: String,
    val email: String?,
    val office: String?
)

fun UserInfoInputModel.toTeacherDbWrite() = TeacherDbWrite(number, name, email!!, office!!)
fun UserInfoInputModel.toStudentDbWrite() = StudentDbWrite(number, name)

/* ******************** SESSION ******************** */

data class UserSession(
    val number: Int,
    val sessionId: String,
    val userType: Char
)