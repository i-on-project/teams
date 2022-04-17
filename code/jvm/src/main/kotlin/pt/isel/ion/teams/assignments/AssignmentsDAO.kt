package pt.isel.ion.teams.assignments

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AssignmentsDAO {

    @SqlQuery("SELECT id,releasedate,description FROM assignments WHERE cid=:classId")
    fun getAllAssignments(@Bind("classId") classId: Int): List<AssignmentDbRead>

    @SqlQuery("SELECT cid,releasedate,description FROM assignments WHERE id=:id")
    fun getAssignment(@Bind("id") id: Int): AssignmentDbRead

    @SqlUpdate("INSERT INTO assignments (releasedate, cid, description) VALUES (:releasedate,:cid,:description)")
    @GetGeneratedKeys
    fun createAssignment(@BindBean assignment: AssignmentDbWrite): AssignmentDbRead

    @SqlUpdate("UPDATE assignments SET releasedate=:releasedate, description=:description WHERE id=:id")
    @GetGeneratedKeys
    fun updateAssignment(@BindBean assignment: AssignmentDbUpdate): Int

}