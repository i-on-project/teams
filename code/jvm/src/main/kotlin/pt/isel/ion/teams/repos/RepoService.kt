package pt.isel.ion.teams.repos

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class RepoService(val jdbi: Jdbi) {

    fun getAllReposByTeamWithPaging(pageSize: Int, pageIndex: Int, teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RepoDAO::class.java).getAllReposByTeamWithPaging(
                pageSize + 1,
                pageIndex * pageSize,
                teamId
            )
        }

    fun getAllReposByTeam(teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RepoDAO::class.java).getAllReposByTeam(teamId)
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

    fun deleteRepo(repoId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(RepoDAO::class.java).deleteRepo(repoId)
        }
}