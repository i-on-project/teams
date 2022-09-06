package pt.isel.ion.teams.assignments

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.InvalidDateFormatException
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.deliveries.DeliveriesService
import pt.isel.ion.teams.deliveries.toCompactOutput

/**
 * Controller responsible for handling request made to the Assignment resource.
 */
@RestController
@RequestMapping(Uris.Assignments.MAIN_PATH)
class AssignmentsController(val assignmentsService: AssignmentsService, val deliveriesService: DeliveriesService) {

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
                    assignmentsService.getAllAssignments(pageIndex, pageSize, classId).map { it.toCompactOutput() },
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
    fun createAssignment(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @RequestBody assignment: AssignmentInputModel
    ): ResponseEntity<Any> {
        try {
            val split = assignment.releaseDate!!.split("T")
            val date = split[0]
            val time = split[1] + ":00"

            assignment.releaseDate = "$date $time"

            val ass = assignmentsService.createAssignment(assignment.toDb(classId))

            return ResponseEntity
                .created(Uris.Assignments.Assignment.make(orgId, classId, ass.id))
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    ass
                )
        } catch (e: IllegalArgumentException) {
            throw InvalidDateFormatException()
        }
    }

    @PutMapping(Uris.Assignments.Assignment.PATH)
    fun updateAssignment(
        @PathVariable assignmentId: Int,
        @RequestBody assignment: AssignmentUpdateModel
    ): ResponseEntity<Any> {
        try {
            if (assignment.releaseDate != null) {
                val split = assignment.releaseDate!!.split("T")
                val date = split[0]
                val time = split[1] + ":00"

                assignment.releaseDate = "$date $time"
            }

            return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(assignmentsService.updateAssignment(assignment.toDb(assignmentId)).toOutput())
        } catch (e: IllegalArgumentException) {
            throw InvalidDateFormatException()
        }
    }


    @DeleteMapping(Uris.Assignments.Assignment.PATH)
    fun deleteAssignment(
        @PathVariable assignmentId: Int
    ): ResponseEntity<Any> {
        assignmentsService.deleteAssignment(assignmentId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}