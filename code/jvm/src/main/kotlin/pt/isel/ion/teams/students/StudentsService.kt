package pt.isel.ion.teams.students

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

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

    fun getStudent(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).getStudent(number)
        }

    fun createStudent(student: StudentDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).createStudent(student)
        }

    fun updateStudent(student: StudentDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).updateStudentName(student)
        }

    fun deleteStudent(number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).deleteStudent(number)
        }

    fun addStudent(student: StudentClassInfoDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).addStudent(student)
        }

    fun removeStudent(number: Int, cid: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(StudentsDAO::class.java).removeStudent(number, cid)
        }



}