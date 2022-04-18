package pt.isel.ion.teams.requests

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/orgs/{orgId}/classrooms/{classroomId}/requests")
class RequestsController(val requestsService: RequestsService) {

    @GetMapping
    fun getAllRequestInClassroom(@PathVariable classId: Int): List<RequestsOutputModel> =
        requestsService.getAllRequestsInClassroom(classId).map { it.toOutput() }

    @GetMapping("/{teamId}")
    fun getRequest(@PathVariable teamId: Int,@PathVariable classroomId: Int): RequestsOutputModel =
        requestsService.getRequest(teamId,classroomId).toOutput()

}
