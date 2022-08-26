package pt.isel.ion.teams.repos.home

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE

@RestController
@RequestMapping(Uris.Home.PATH)
class HomeController(
    //@RequestParam clientId: String
) {

    @GetMapping
    fun getHome(): ResponseEntity<Any> {

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                HomeSirenObject()
            )
    }

}