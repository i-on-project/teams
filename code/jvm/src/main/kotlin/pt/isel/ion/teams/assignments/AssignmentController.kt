package pt.isel.ion.teams.assignments

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.CollectionModel
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.daw.project.common.siren.SirenEntity
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.deliveries.DeliveriesService
import pt.isel.ion.teams.deliveries.toCompactOutput

@RestController
@RequestMapping(Uris.Assignments.MAIN_PATH)
class AssignmentController(val assignmentsService: AssignmentsService, val deliveriesService: DeliveriesService) {

    @GetMapping
    fun getAllAssignments(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ) =
        ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                CollectionModel(pageIndex, pageSize).toAssignmentsSirenObject(
                    assignmentsService.getAllAssignments(classId).map { it.toCompactOutput() },
                    orgId,
                    classId
                )
            )

    @GetMapping(Uris.Assignments.Assignment.PATH)
    fun getAssignment(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable assignmentId: Int
    ): ResponseEntity<Any> {
        val assignment = assignmentsService.getAssignment(assignmentId).toOutput()
        val deliveries = deliveriesService.getAllDeliveriesOfAssignment(10, 0, assignmentId)
            .map { it.toCompactOutput() }

        //TODO Detect if user is student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                assignment.toTeacherSirenObject(
                    deliveries,
                    orgId,
                    classId
                )
            )

    }

    @PostMapping
    fun createAssignment(@PathVariable classId: Int, @RequestBody assignmentInputModel: AssignmentInputModel) =
        assignmentsService.createAssignment(assignmentInputModel.toDb(classId))

    @PutMapping(Uris.Assignments.Assignment.PATH)
    fun updateAssignment(
        @PathVariable assignmentId: Int,
        @RequestBody assignmentUpdateModel: AssignmentUpdateModel
    ): Int =
        assignmentsService.updateAssignment(assignmentUpdateModel.toDb(assignmentId))
}