package pt.isel.ion.teams.teacher

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class TeacherService(val jdbi: Jdbi) {

    fun getTeachers(classroomId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeacherDAO::class.java).getTeachers(classroomId)
        }

    fun getTeacher(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeacherDAO::class.java).getTeacher(number)
        }

    fun createTeacher(teacher: TeacherDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(TeacherDAO::class.java).createTeacher(teacher)
        }

    fun updateTeacher(teacher: TeacherDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(TeacherDAO::class.java).updateTeacher(teacher)
        }
}