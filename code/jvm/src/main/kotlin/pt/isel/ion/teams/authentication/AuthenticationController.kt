package pt.isel.ion.teams.authentication

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping
class AuthenticationController {

    @GetMapping(Uris.Login.PATH)
    fun getLogin() =
        RedirectView()
}