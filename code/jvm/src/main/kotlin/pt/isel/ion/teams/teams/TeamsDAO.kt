package pt.isel.ion.teams.teams

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import pt.isel.ion.teams.common.Uris

interface TeamsDAO {

    @SqlQuery("SELECT * FROM teams_view WHERE cid=:classroomId LIMIT :limit OFFSET :offset")
    fun getAllTeamsOfClassroom(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("classroomId") classroomId: Int
    ): List<TeamsDbRead>

    @SqlQuery("SELECT * FROM teams_view WHERE id=:teamId AND cid=:cId")
    fun getTeam(@Bind("teamId") teamId: Int,@Bind("cId") cId: Int): TeamsDbRead

    @SqlUpdate("INSERT INTO teams (name,cid) VALUES (:name,:cid) ON CONFLICT (name,cid) DO UPDATE SET deleted = B'0', state='pending'")
    @GetGeneratedKeys
    fun createTeam(@BindBean teamsDbWrite: TeamsDbWrite): TeamsDbRead

    @SqlUpdate("UPDATE teams SET name = coalesce(:name, name), state = coalesce(:state, state) WHERE id = :id")
    @GetGeneratedKeys
    fun updateTeam(@BindBean teamsDbUpdate: TeamsDbUpdate): TeamsDbRead

    @SqlUpdate("UPDATE teams SET deleted = B'1' WHERE id = :id")
    fun deleteTeam(@Bind("id") teamId: Int)
}