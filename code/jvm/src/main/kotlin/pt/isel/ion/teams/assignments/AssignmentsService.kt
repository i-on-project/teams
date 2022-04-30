package pt.isel.ion.teams.assignments

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class AssignmentsService(val jdbi: Jdbi) {

    fun getAllAssignments(classId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).getAllAssignments(classId)
        }

    fun getAssignment(@Bind("id") id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).getAssignment(id)
        }

    fun createAssignment(@BindBean assignment: AssignmentDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).createAssignment(assignment)
        }

    fun updateAssignment(@BindBean assignment: AssignmentDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).updateAssignment(assignment)
        }
}