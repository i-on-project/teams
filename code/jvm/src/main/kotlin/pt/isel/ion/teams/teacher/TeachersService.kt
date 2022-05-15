package pt.isel.ion.teams.teacher

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class TeachersService(val jdbi: Jdbi) {

    fun getTeachers(classId: Int, pageSize: Int, pageIndex: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).getTeachers(pageSize + 1, pageIndex * pageSize,classId)
        }

    fun getTeacher(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).getTeacher(number)
        }

    fun createTeacher(teacher: TeacherDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).createTeacher(teacher)
        }

    fun updateTeacher(teacher: TeacherDbUpdate) =
        sqlExceptionHandler {
            if (teacher.cId == null)
                jdbi.onDemand(TeachersDAO::class.java).updateTeacherInfo(teacher).toOutput()
            else
                jdbi.onDemand(TeachersDAO::class.java).updateTeacherClass(teacher).toOutput()
        }

    fun deleteTeacher(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeachersDAO::class.java).deleteTeacher(number)
        }
}