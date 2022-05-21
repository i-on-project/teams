package pt.isel.ion.teams.home

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.classrooms.toSimpleOutput
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE

@RestController
@RequestMapping(Uris.Home.PATH)
class HomeController(val service: HomeService) {

    @GetMapping
    fun getHome(
        number: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<Any> {
        //TODO: check if its student or teacher
        val classrooms = service.getHomeStudent(pageSize, pageIndex, number).map{ it.toSimpleOutput()}

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                CollectionModel(pageIndex, pageSize).toStudentHomeSirenObject(classrooms)
            )
    }

}