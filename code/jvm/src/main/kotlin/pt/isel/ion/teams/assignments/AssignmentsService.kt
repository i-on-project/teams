package pt.isel.ion.teams.assignments

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class AssignmentsService(val jdbi: Jdbi) {

    fun getAllAssignments(pageIndex: Int, pageSize: Int, classId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java)
                .getAllAssignments(pageSize + 1, pageIndex * pageSize, classId)
        }

    fun getAssignment(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).getAssignment(id)
        }

    fun createAssignment(assignment: AssignmentDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).createAssignment(assignment)
        }

    fun updateAssignment(assignment: AssignmentDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).updateAssignment(assignment)
        }

    fun deleteAssignment(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(AssignmentsDAO::class.java).deleteAssignment(id)
        }
}