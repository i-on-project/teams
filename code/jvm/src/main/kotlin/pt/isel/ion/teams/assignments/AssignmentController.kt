package pt.isel.ion.teams.assignments

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Assignments.PATH)
class AssignmentController(val assignmentsService: AssignmentsService) {

    @GetMapping
    fun getAllAssignmenst(@PathVariable classId: Int): List<AssignmentOutputModel> =
        assignmentsService.getAllAssignments(classId).map { it.toOutput() }

    @GetMapping(Uris.Assignments.Assignment.PATH)
    fun getAssignment(@PathVariable assignmentId: Int): AssignmentOutputModel =
        assignmentsService.getAssignment(assignmentId).toOutput()

    @PostMapping
    fun createAssignment(@RequestBody assignmentInputModel: AssignmentInputModel) =
        assignmentsService.createAssignment(assignmentInputModel.toDb())

    @PutMapping(Uris.Assignments.Assignment.PATH)
    fun updateAssignment(@PathVariable id: Int, @RequestBody assignmentUpdateModel: AssignmentUpdateModel): Int =
        assignmentsService.updateAssignment(assignmentUpdateModel.toDb(id))
}