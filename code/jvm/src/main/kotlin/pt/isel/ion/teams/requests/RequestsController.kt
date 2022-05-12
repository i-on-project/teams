package pt.isel.ion.teams.requests

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Requests.MAIN_PATH)
class RequestsController(val requestsService: RequestsService) {

    @GetMapping
    fun getAllRequestInClassroom(@PathVariable classId: Int): List<RequestsOutputModel> =
        requestsService.getAllRequestsInClassroom(classId).map { it.toOutput() } //TODO: siren

    @GetMapping(Uris.Requests.Request.PATH)
    fun getRequest(@PathVariable teamId: Int,@PathVariable classId: Int): RequestsOutputModel =
        requestsService.getRequest(teamId,classId).toOutput() //TODO: siren

    @PutMapping(Uris.Requests.Request.PATH)
    fun acceptRequest(@PathVariable teamId: Int,@PathVariable classId: Int): ResponseEntity<Any> {
        requestsService.acceptRequest(teamId, classId)

        return ResponseEntity
            .ok()
            .body(null)
    }

    @DeleteMapping(Uris.Requests.Request.PATH)
    fun declineRequest(@PathVariable teamId: Int,@PathVariable classId: Int): ResponseEntity<Any> {
        requestsService.declineRequest(teamId, classId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}
