package pt.isel.ion.teams.authentication

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class AuthenticationService(val jdbi: Jdbi) {

    /* User Session */

    fun createSession(@Bind number: Int, @Bind sessionId: String, @Bind usertype: Char) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).createSession(number, sessionId, usertype)
        }
    }

    fun deleteSession(@Bind number: Int, @Bind sessionId: String) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).deleteSession(number, sessionId)
        }
    }

    /* Verification */

    fun verifyTeacher(number: Int) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).verifyTeacher(number)
        }
    }

    fun verifyStudent(number: Int) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).verifyStudent(number)
        }
    }

    fun isVerifiedTeacher(@Bind number: Int) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).isVerifiedTeacher(number)
        }
    }

    fun isVerifiedStudent(@Bind number: Int) {
        sqlExceptionHandler {
            jdbi.onDemand(AuthenticationDAO::class.java).isVerifiedStudent(number)
        }
    }
}