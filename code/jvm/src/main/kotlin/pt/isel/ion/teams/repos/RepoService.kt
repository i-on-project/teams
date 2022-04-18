package pt.isel.ion.teams.repos

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class RepoService(val jdbi: Jdbi) {

    fun getAllReposByTeam(teamId: Int) = jdbi.onDemand(RepoDAO::class.java).getAllReposByRTeam(teamId)

    fun getRepo(id: Int) = jdbi.onDemand(RepoDAO::class.java).getRepo(id)

    fun createRepo(repoDbWrite: RepoDbWrite) = jdbi.onDemand(RepoDAO::class.java).createRepo(repoDbWrite)

    fun updateRepo(repoDbUpdate: RepoDbUpdate) = jdbi.onDemand(RepoDAO::class.java).updateRepo(repoDbUpdate)
}