package pt.isel.ion.teams.repos

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class RepoService(val jdbi: Jdbi) {

    fun getAllReposByTeam(teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RepoDAO::class.java).getAllReposByRTeam(teamId)
        }

    fun getRepo(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RepoDAO::class.java).getRepo(id)
        }

    fun createRepo(repoDbWrite: RepoDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(RepoDAO::class.java).createRepo(repoDbWrite)
        }

    fun updateRepo(repoDbUpdate: RepoDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(RepoDAO::class.java).updateRepo(repoDbUpdate)
        }
}