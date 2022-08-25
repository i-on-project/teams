package pt.isel.ion.teams.authentication

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler
import java.io.File

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
    fun getUserFromSession(sessionId: String){
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).getUserFromSession(sessionId)
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