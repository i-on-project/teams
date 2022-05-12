package pt.isel.ion.teams.requests

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface RequestsDAO {

    @SqlQuery("SELECT * FROM teams WHERE cid=:classId AND state='pending'")
    fun getAllRequestsInClass(@Bind("classId") classId: Int): List<RequestsDbRead>

    @SqlQuery("SELECT * FROM teams WHERE id=:teamId AND cid=:classId AND state='pending'")
    fun getRequest(@Bind("teamId") teamId: Int, @Bind("classId") classId: Int): RequestsDbRead
}