package pt.isel.ion.teams.requests

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Requests.MAIN_PATH)
class RequestsController(val requestsService: RequestsService) {

    @GetMapping
    fun getAllRequestInClassroom(@PathVariable classroomId: Int): List<RequestsOutputModel> =
        requestsService.getAllRequestsInClassroom(classroomId).map { it.toOutput() }

    @GetMapping(Uris.Requests.Request.PATH)
    fun getRequest(@PathVariable teamId: Int,@PathVariable classroomId: Int): RequestsOutputModel =
        requestsService.getRequest(teamId,classroomId).toOutput()

}
