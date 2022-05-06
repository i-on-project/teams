package pt.isel.ion.teams.repos

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface RepoDAO {

    @SqlQuery("SELECT * FROM repos_view WHERE tid=:teamId LIMIT :limit OFFSET :offset")
    fun getAllReposByTeamWithPaging(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("teamId") teamId: Int
    ): List<RepoDbRead>

    @SqlQuery("SELECT * FROM repos_view WHERE tid=:teamId")
    fun getAllReposByTeam(
        @Bind("teamId") teamId: Int
    ): List<RepoDbRead>

    @SqlQuery("SELECT * FROM repos_view WHERE id=:id")
    fun getRepo(@Bind("id") id: Int): RepoDbRead

    @SqlUpdate("INSERT INTO repos (name, url, tid, assid) VALUES (:name, :url, :tid, :assid)")
    @GetGeneratedKeys
    fun createRepo(@BindBean repoDbWrite: RepoDbWrite): RepoDbRead

    @SqlUpdate("UPDATE repos SET name = coalesce(:name, name), assid = coalesce(:assId, assid), url = coalesce(:url, url) WHERE id=:id")
    @GetGeneratedKeys
    fun updateRepo(@BindBean repo: RepoDbUpdate): RepoDbRead

    @SqlUpdate("UPDATE repos SET deleted = B'1' WHERE id =: id")
    fun deleteRepo(@Bind("id") repoId: Int)
}