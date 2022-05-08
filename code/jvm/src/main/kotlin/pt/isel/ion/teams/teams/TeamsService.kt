package pt.isel.ion.teams.teams

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

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