package pt.isel.ion.teams.authentication

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler
import java.io.File

/**
 * Service for the authentication process. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO and sending confirmation emails to new users.
 */
@Component
class AuthenticationService(val jdbi: Jdbi) {

    /* User Session */

    fun createSession(number: Int, sessionId: String,  usertype: Char): UserSession {
        return sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).createSession(number, sessionId, usertype)
        }
    }

    fun deleteSession(sessionId: String) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).deleteSession(sessionId)
        }
    }
    fun getNumber(sessionId: String): Int {
        return sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).getNumber(sessionId)
        }
    }

    /* Verification */

    fun createVerification(code: String, number: Int) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).createVerification(code, number)
        }
    }

    fun verifyUser(code: String) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).verifyUser(code)
        }
    }

    /* Session verification. */
    fun getUserFromSession(sessionId: String): Int{
        return sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).getUserFromSession(sessionId)
        }
    }

    fun getUserTypeFromSession(sessionId: String): Char {
        return sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).getUserTypeFromSession(sessionId)
        }
    }

    /* Teacher email verification. */
    fun getVerificationId( number: Int): String {
        return sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).getVerificationId(number)
        }
    }

    fun checkIsAuthorisedTeacher(email: String): Boolean {
        val emails = File("allowed_teachers.txt").readLines(Charsets.UTF_8)

        emails.forEach {
            val readEmail = it.split(";")[0]
            if (readEmail == email) return true
        }

        return false
    }
}