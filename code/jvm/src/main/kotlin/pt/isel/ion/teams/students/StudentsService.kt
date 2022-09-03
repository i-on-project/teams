package pt.isel.ion.teams.students

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

/**
 * Service for the Student resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
@Component
class StudentsService(val jdbi: Jdbi) {

    fun getAllStudentsByClassroom(pageSize: Int, pageIndex: Int, classroomId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java)
                .getAllStudentsByClassroom(classroomId, pageSize + 1, pageIndex * pageSize)
        }

    fun getAllStudentsByTeam(teamId: Int, pageSize: Int, pageIndex: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).getAllStudentsByTeam(teamId, pageSize + 1, pageIndex * pageSize)
        }

    fun getAllTeamsByStudent(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).getAllTeamsByStudent(number)
        }

    fun getStudent(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).getStudent(number)
        }

    fun getStudentByUsername(username: String) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).getStudentByUsername(username)
        }

    fun createStudent(student: StudentDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).createStudent(student)
        }

    fun updateStudent(student: StudentDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).updateStudentName(student)
        }

    fun updateStudentUsername(student: StudentDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).updateStudentUsername(student)
        }

    fun deleteStudent(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).deleteStudent(number)
        }

    fun addStudent(number: Int, tid: Int, cid: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).addStudent(number,tid,cid)
        }

    fun removeStudent(number: Int, cid: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).removeStudent(number, cid)
        }



}