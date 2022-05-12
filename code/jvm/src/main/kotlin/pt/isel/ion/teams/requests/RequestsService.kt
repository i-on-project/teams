package pt.isel.ion.teams.requests

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler
import pt.isel.ion.teams.repos.RepoDAO
import pt.isel.ion.teams.repos.RepoDbWrite
import pt.isel.ion.teams.teams.TeamsDAO
import pt.isel.ion.teams.teams.TeamsDbUpdate

@Component
class RequestsService(val jdbi: Jdbi) {

    fun getAllRequestsInClassroom(classroomId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RequestsDAO::class.java).getAllRequestsInClass(classroomId)
        }

    fun getRequest(teamId: Int, classroomId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RequestsDAO::class.java).getRequest(teamId, classroomId)
        }

    fun acceptRequest(teamId: Int, classId: Int) {
        jdbi.onDemand(TeamsDAO::class.java).updateTeam(TeamsDbUpdate(teamId,null,"active"))

        //TODO: create team (on DB and GitHub)
        //TODO: create Repo (on DB and GitHub)
        //jdbi.onDemand(RepoDAO::class.java).createRepo(RepoDbWrite(url,name,teamId, assId))
    }

    fun declineRequest(teamId: Int, classId: Int) {
        jdbi.onDemand(TeamsDAO::class.java).deleteTeam(teamId)
    }
}