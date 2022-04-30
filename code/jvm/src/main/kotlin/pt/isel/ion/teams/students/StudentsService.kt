package pt.isel.ion.teams.students

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class StudentsService(val jdbi: Jdbi) {

    fun getAllStudentsByClassroom(classroomId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).getAllStudentsByClassroom(classroomId)
        }

    fun getAllStudentsByTeam(teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).getAllStudentsByTeam(teamId)
        }

    fun createStudent( student: StudentDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).createStudent(student)
        }

    fun updateStudent(student: StudentDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).updateStudent(student)
        }
}