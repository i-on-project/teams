package pt.isel.ion.teams.students

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Component

@Component
class StudentsService(val jdbi: Jdbi) {

    fun getAllStudentsByClassroom(classroomId: Int) =
        jdbi.onDemand(StudentsDAO::class.java).getAllStudentsByClassroom(classroomId)

    fun getAllStudentsByTeam(teamId: Int) =
        jdbi.onDemand(StudentsDAO::class.java).getAllStudentsByTeam(teamId)

    fun createStudent( student: StudentDbWrite) =
        jdbi.onDemand(StudentsDAO::class.java).createStudent(student)

    fun updateStudent(student: StudentDbUpdate) =
        jdbi.onDemand(StudentsDAO::class.java).updateStudent(student)
}