package pt.isel.ion.teams.organizations

import org.jdbi.v3.core.Jdbi
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/orgs")
class OrganizationController(val jdbi: Jdbi) {

    @GetMapping
    fun getAllOrganizations() = jdbi.onDemand(OrganizationDAO::class.java).getAllOrganizations()

}