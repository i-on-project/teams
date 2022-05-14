package pt.isel.ion.teams.repos

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class ReposService(val jdbi: Jdbi) {

    fun getAllReposByTeamWithPaging(pageSize: Int, pageIndex: Int, teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ReposDAO::class.java).getAllReposByTeamWithPaging(
                pageSize + 1,
                pageIndex * pageSize,
                teamId
            )
        }

    fun getAllReposByTeam(teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ReposDAO::class.java).getAllReposByTeam(teamId)
        }

    fun getRepo(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ReposDAO::class.java).getRepo(id)
        }

    fun createRepo(repoDbWrite: RepoDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(ReposDAO::class.java).createRepo(repoDbWrite)
        }

    fun updateRepo(repoDbUpdate: RepoDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(ReposDAO::class.java).updateRepo(repoDbUpdate)
        }

    fun deleteRepo(repoId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ReposDAO::class.java).deleteRepo(repoId)
        }
}