package pt.isel.ion.teams.teams

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler
import pt.isel.ion.teams.students.StudentsDAO

/**
 * Service for the Team resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
@Component
class TeamsService(val jdbi: Jdbi) {

    fun getAllTeamsOfClassroom(pageSize: Int, pageIndex: Int, classroomId: Int) =
        sqlExceptionHandler {
            jdbi
                .onDemand(TeamsDAO::class.java)
                .getAllTeamsOfClassroom(pageSize + 1, pageIndex * pageSize, classroomId)
        }

    fun getTeam(teamId: Int, classId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).getTeam(teamId,classId)
        }

    fun createTeam(teamsDbWrite: TeamsDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).createTeam(teamsDbWrite)
        }

    fun updateTeam(teamsDbUpdate: TeamsDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).updateTeam(teamsDbUpdate)
        }

    fun deleteTeam(teamId: Int) {
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).deleteTeam(teamId)
        }
    }
}