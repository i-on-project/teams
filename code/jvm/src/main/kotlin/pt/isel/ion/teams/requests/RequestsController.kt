package pt.isel.ion.teams.requests

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE

@RestController
@RequestMapping(Uris.Requests.MAIN_PATH)
class RequestsController(val requestsService: RequestsService) {

    @GetMapping
    fun getAllRequestInClassroom(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable classId: Int,
        @PathVariable orgId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize).toRequestSirenObject(
                requestsService.getAllRequestsInClassroom(pageIndex, pageSize, classId).map { it.toOutput() },
                classId,
                orgId
            )
        )

    @PutMapping(Uris.Requests.Request.PATH)
    fun acceptRequest(@PathVariable teamId: Int, @PathVariable classId: Int): ResponseEntity<Any> {
        requestsService.acceptRequest(teamId, classId)

        return ResponseEntity
            .ok()
            .body(null)
    }

    @DeleteMapping(Uris.Requests.Request.PATH)
    fun declineRequest(@PathVariable teamId: Int, @PathVariable classId: Int): ResponseEntity<Any> {
        requestsService.declineRequest(teamId, classId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}
