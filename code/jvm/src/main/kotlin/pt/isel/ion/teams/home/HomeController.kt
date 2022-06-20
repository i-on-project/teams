package pt.isel.ion.teams.home

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.classrooms.toSimpleOutput
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.organizations.OrganizationsService
import pt.isel.ion.teams.teacher.toTeachersSirenObject

@RestController
@RequestMapping(Uris.Home.PATH)
class HomeController(val organizationsService: OrganizationsService, val classroomService: OrganizationsService) {

    @GetMapping
    fun getHome(
        //number: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<Any> {
        //TODO: check if its student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                TeacherHomeSirenObject()
            )
    }

}