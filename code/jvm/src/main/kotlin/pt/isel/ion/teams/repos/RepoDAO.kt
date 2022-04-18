package pt.isel.ion.teams.repos

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface RepoDAO {

    @SqlQuery("SELECT * FROM repos WHERE tid=:teamId")
    fun getAllReposByRTeam(@Bind("teamId") teamId: Int): List<RepoDbRead>

    @SqlQuery("SELECT * FROM repos WHERE id=:id")
    fun getRepo(@Bind("id") id: Int): RepoDbRead

    @SqlUpdate("INSERT INTO repos (name,url,tid,assid) VALUES (:name,:url,:tid,:assid)")
    @GetGeneratedKeys
    fun createRepo(@BindBean repoDbWrite: RepoDbWrite): RepoDbRead

    @SqlUpdate("UPDATE repos SET name=:name,tid=:tid,assid=:id WHERE id=:id")
    @GetGeneratedKeys
    fun updateRepo(@BindBean repo: RepoDbUpdate): Int
}