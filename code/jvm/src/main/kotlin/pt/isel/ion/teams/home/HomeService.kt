package pt.isel.ion.teams.home

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.classrooms.ClassroomDbRead
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class HomeService(val jdbi: Jdbi) {

    fun getHomeTeacher(pageSize: Int, pageIndex: Int, number: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(HomeDAO::class.java).getTeacherOrganizations(pageSize + 1, pageIndex * pageSize, number)
        }

    fun getHomeStudent(pageSize: Int, pageIndex: Int, number: Int)=
        sqlExceptionHandler {
            jdbi.onDemand(HomeDAO::class.java).getStudentClassrooms(pageSize + 1, pageIndex * pageSize, number)
        }

}