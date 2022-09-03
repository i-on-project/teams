package pt.isel.ion.teams.teacher

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

/**
 * Service for the Teacher resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
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
            if (simpleTeacherDbRead.cid == null)
                jdbi.onDemand(TeachersDAO::class.java).addTeacherToOrganization(simpleTeacherDbRead)
            else {
                jdbi.onDemand(TeachersDAO::class.java).addTeacherToOrganization(simpleTeacherDbRead)
                jdbi.onDemand(TeachersDAO::class.java).addTeacherToClassroom(simpleTeacherDbRead)
            }
        }
    fun removeTeacher(simpleTeacherDbRead: SimpleTeacherDbRead) =
        sqlExceptionHandler {
            if (simpleTeacherDbRead.cid == null)
                jdbi.onDemand(TeachersDAO::class.java).removeTeacherFromOrganization(simpleTeacherDbRead)
            else {
                jdbi.onDemand(TeachersDAO::class.java).removeTeacherFromClassroom(simpleTeacherDbRead)
                jdbi.onDemand(TeachersDAO::class.java).removeTeacherFromOrganization(simpleTeacherDbRead)
            }
        }
}