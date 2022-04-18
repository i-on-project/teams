package pt.isel.ion.teams.teams

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TeamsDAO {

    @SqlQuery("SELECT * FROM teams WHERE cid=:classroomId AND state!='pending'")
    fun getAllTeamsOfClassroom(@Bind("classroomId") classroomId: Int): List<TeamsDbRead>

    @SqlQuery("SELECT * FROM teams WHERE id=:teamId AND cid=:classroomId AND state!='pending'")
    fun getTeam(@Bind("teamId") teamId: Int): TeamsDbRead

    @SqlUpdate("INSERT INTO teams (name,cid,state) VALUES (:name,:cid,:state)")
    @GetGeneratedKeys
    fun createTeam(@BindBean teamsDbWrite: TeamsDbWrite)

    @SqlUpdate("UPDATE teams SET name=:name,cid=:cid ,state=:state WHERE id=:id")
    @GetGeneratedKeys
    fun updateTeam(@BindBean teamsDbUpdate: TeamsDbUpdate): Int

}