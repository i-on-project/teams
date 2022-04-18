package pt.isel.ion.teams.requests

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface RequestsDAO {

    @SqlQuery("SELECT * FROM teams WHERE cid=:classroomId AND state='pending'")
    fun getAllRequestsInClass(@Bind("classroomId") classroomId: Int): List<RequestsDbRead>

    @SqlQuery("SELECT * FROM teams WHERE id=:teamId AND cid=:classroomId AND state='pending'")
    fun getRequest(@Bind("teamId") teamId: Int, @Bind("classroomId") classroomId: Int): RequestsDbRead

}