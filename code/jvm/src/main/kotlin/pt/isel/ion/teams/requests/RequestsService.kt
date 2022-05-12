package pt.isel.ion.teams.requests

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler
import pt.isel.ion.teams.teams.TeamsDAO
import pt.isel.ion.teams.teams.TeamsDbUpdate

@Component
class RequestsService(val jdbi: Jdbi) {

    fun getAllRequestsInClassroom(pageIndex: Int, pageSize: Int, classroomId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RequestsDAO::class.java)
                .getAllRequestsInClass(pageSize + 1, pageIndex * pageSize, classroomId)
        }

    fun acceptRequest(teamId: Int, classId: Int) {
        jdbi.onDemand(TeamsDAO::class.java).updateTeam(TeamsDbUpdate(teamId, null, "active"))

        //TODO: create team (on DB and GitHub)
        //TODO: create Repo (on DB and GitHub)
        //jdbi.onDemand(RepoDAO::class.java).createRepo(RepoDbWrite(url,name,teamId, assId))
    }

    fun declineRequest(teamId: Int, classId: Int) {
        jdbi.onDemand(TeamsDAO::class.java).deleteTeam(teamId)
    }
}