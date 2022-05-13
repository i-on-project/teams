package pt.isel.ion.teams.teacher

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class TeacherService(val jdbi: Jdbi) {

    fun getTeachers(classId: Int, pageSize: Int, pageIndex: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeacherDAO::class.java).getTeachers(pageSize + 1, pageIndex * pageSize,classId)
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
            if (teacher.cId == null)
                jdbi.onDemand(TeacherDAO::class.java).updateTeacherInfo(teacher).toOutput()
            else
                jdbi.onDemand(TeacherDAO::class.java).updateTeacherClass(teacher).toOutput()
        }
}