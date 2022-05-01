package pt.isel.ion.teams.teams

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class TeamsService(val jdbi: Jdbi) {

    fun getAllTeamsOfOrganization(classroomId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).getAllTeamsOfClassroom(classroomId)
        }

    fun getTeam(teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).getTeam(teamId)
        }

    fun createTeam(teamsDbWrite: TeamsDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).createTeam(teamsDbWrite)
        }

    fun updateTeam(teamsDbUpdate: TeamsDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(TeamsDAO::class.java).updateTeam(teamsDbUpdate)
        }
}