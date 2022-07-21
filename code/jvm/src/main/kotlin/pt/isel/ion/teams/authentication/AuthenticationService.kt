package pt.isel.ion.teams.authentication

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class AuthenticationService(val jdbi: Jdbi) {

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