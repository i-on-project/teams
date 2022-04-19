package pt.isel.ion.teams.requests

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.teams.TeamsDAO

@Component
class RequestsService(val jdbi: Jdbi) {

    fun getAllRequestsInClassroom(classroomId: Int) =
        jdbi.onDemand(RequestsDAO::class.java).getAllRequestsInClass(classroomId)

    fun getRequest(teamId: Int, classroomId: Int) =
        jdbi.onDemand(RequestsDAO::class.java).getRequest(teamId, classroomId)

    fun acceptRequest(teamId: Int, classroomId: Int) = run {
        //jdbi.onDemand(TeamsDAO::class.java).updateTeam(TeamDbUpdate())

        //TODO: create Repo on DB and GitHub
        // jdbi.onDemand(RepoDAO::class.java).createRepo(RepoDbWrite())
    }

    fun declineRequest(teamId: Int, classroomId: Int) = run {
        //TODO: Delete team
        // jdbi.onDemand(TeamsDAO::class.java).deleteTeam(teamId)
    }
}