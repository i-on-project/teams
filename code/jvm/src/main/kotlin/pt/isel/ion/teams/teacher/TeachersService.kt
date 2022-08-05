package pt.isel.ion.teams.teacher

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class TeachersService(val jdbi: Jdbi) {

    fun getTeachersByClass(classId: Int, pageSize: Int, pageIndex: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).getTeachersByClass(pageSize + 1, pageIndex * pageSize,classId)
        }

    fun getTeacher(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).getTeacher(number)
        }

    fun getTeacherByUsername(username: String) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).getTeacherByUsername(username)
        }

    fun createTeacher(teacher: TeacherDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).createTeacher(teacher)
        }

    fun updateTeacher(teacher: TeacherDbUpdate) =
        sqlExceptionHandler {
                jdbi.onDemand(TeachersDAO::class.java).updateTeacherInfo(teacher)
        }

    fun deleteTeacher(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).deleteTeacher(number)
        }

    fun addTeacher(simpleTeacherDbRead: SimpleTeacherDbRead) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).addTeacher(simpleTeacherDbRead)
        }
    fun removeTeacher(simpleTeacherDbRead: SimpleTeacherDbRead) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).removeTeacher(simpleTeacherDbRead)
        }
}