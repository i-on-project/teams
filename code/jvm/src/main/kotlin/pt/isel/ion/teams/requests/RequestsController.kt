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
    fun getAllRequestInClassroom(@PathVariable classId: Int): List<RequestsOutputModel> =
        requestsService.getAllRequestsInClassroom(classId).map { it.toOutput() }

    @GetMapping(Uris.Requests.Request.PATH)
    fun getRequest(@PathVariable teamId: Int,@PathVariable classId: Int): RequestsOutputModel =
        requestsService.getRequest(teamId,classId).toOutput()

}
