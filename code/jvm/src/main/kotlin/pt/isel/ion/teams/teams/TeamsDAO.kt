package pt.isel.ion.teams.teams

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import pt.isel.ion.teams.common.Uris

interface TeamsDAO {

    @SqlQuery("SELECT * FROM teams_view WHERE cid=:classroomId AND state!='pending' LIMIT :limit OFFSET :offset")
    fun getAllTeamsOfClassroom(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("classroomId") classroomId: Int
    ): List<TeamsDbRead>

    @SqlQuery("SELECT * FROM teams_view WHERE id=:teamId AND cid=:classroomId AND state!='pending'")
    fun getTeam(@Bind("teamId") teamId: Int): TeamsDbRead

    @SqlUpdate("INSERT INTO teams (name,cid,state) VALUES (:name,:cid,:state)")
    @GetGeneratedKeys
    fun createTeam(@BindBean teamsDbWrite: TeamsDbWrite): TeamsDbRead

    @SqlUpdate("UPDATE teams SET name=:name,cid=:cid ,state=:state WHERE id=:id")
    @GetGeneratedKeys
    fun updateTeam(@BindBean teamsDbUpdate: TeamsDbUpdate): TeamsDbRead

    @SqlUpdate("UPDATE teams SET deleted = B'1' WHERE id =: id")
    fun deleteTeam(@Bind("id") teamId: Int)
}