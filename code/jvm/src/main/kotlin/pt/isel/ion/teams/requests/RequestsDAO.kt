package pt.isel.ion.teams.requests

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery

/**
 * Data Access Object for the Request resource. This is an interface responsible for defining the methods for
 * accessing data of the given resource.
 */
interface RequestsDAO {

    @SqlQuery("SELECT * FROM requests_view WHERE cid=:classId LIMIT :limit OFFSET :offset")
    fun getAllRequestsInClass(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("classId") classId: Int
    ): List<RequestsDbRead>

}