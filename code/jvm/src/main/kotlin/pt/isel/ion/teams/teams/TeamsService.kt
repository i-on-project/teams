package pt.isel.ion.teams.teams

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.springframework.stereotype.Component

@Component
class TeamsService(val jdbi: Jdbi) {

    fun getAllTeamsOfOrganization(classroomId: Int) =
        jdbi.onDemand(TeamsDAO::class.java).getAllTeamsOfClassroom(classroomId)

    fun getTeam(teamId: Int) =
        jdbi.onDemand(TeamsDAO::class.java).getTeam(teamId)

    fun createTeam(teamsDbWrite: TeamsDbWrite) =
        jdbi.onDemand(TeamsDAO::class.java).createTeam(teamsDbWrite)

    fun updateTeam(teamsDbUpdate: TeamsDbUpdate) =
        jdbi.onDemand(TeamsDAO::class.java).updateTeam(teamsDbUpdate)
}