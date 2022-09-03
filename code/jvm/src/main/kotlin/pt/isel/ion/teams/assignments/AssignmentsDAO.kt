package pt.isel.ion.teams.assignments

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Data Access Object for the Assignment resource. This is an interface responsible for defining the methods for
 * accessing data of the given resource.
 */
interface AssignmentsDAO {

    @SqlQuery("SELECT * FROM assignments_view WHERE cid=:classId")
    fun getAllAssignments(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("classId") classId: Int
    ): List<AssignmentDbRead>

    @SqlQuery("SELECT * FROM assignments_view WHERE id=:id")
    fun getAssignment(@Bind("id") id: Int): AssignmentDbRead

    @SqlUpdate("INSERT INTO assignments (releasedate, cid, name, description) VALUES (:releaseDate,:cid, :name, :description)")
    @GetGeneratedKeys
    fun createAssignment(@BindBean assignment: AssignmentDbWrite): AssignmentDbRead

    @SqlUpdate("UPDATE assignments SET releasedate = COALESCE(:releaseDate,releasedate), name = COALESCE(:name,name), description = COALESCE(:description,description), cid = COALESCE(:cid,cid) WHERE id=:id")
    @GetGeneratedKeys
    fun updateAssignment(@BindBean assignment: AssignmentDbUpdate): AssignmentDbRead

    @SqlUpdate("UPDATE assignments SET deleted=B'1' WHERE id=:assId")
    fun deleteAssignment(@Bind("assId") assId: Int)
}