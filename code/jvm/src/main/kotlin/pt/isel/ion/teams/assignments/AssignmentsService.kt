package pt.isel.ion.teams.assignments

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Component

@Component
class AssignmentsService(val jdbi: Jdbi) {

    fun getAllAssignments(classId: Int) = jdbi.onDemand(AssignmentsDAO::class.java).getAllAssignments(classId)

    fun getAssignment(@Bind("id") id: Int) = jdbi.onDemand(AssignmentsDAO::class.java).getAssignment(id)

    fun createAssignment(@BindBean assignment: AssignmentDbWrite) =
        jdbi.onDemand(AssignmentsDAO::class.java).createAssignment(assignment)

    fun updateAssignment(@BindBean assignment: AssignmentDbUpdate) =
        jdbi.onDemand(AssignmentsDAO::class.java).updateAssignment(assignment)
}