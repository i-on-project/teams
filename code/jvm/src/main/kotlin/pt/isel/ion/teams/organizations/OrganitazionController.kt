package pt.isel.ion.teams.organizations

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/orgs")
class OrganizationController(val organizationService: OrganizationService) {

    @GetMapping
    fun getAllOrganizations() = organizationService.getAllOrganizations()

    @GetMapping("{id}")
    fun getOrganization(@PathVariable("id") id: Int) = organizationService.getOrganization(id)

    @PostMapping
    fun createOrganization(organization: OrganizationInput) = organizationService.createOrganization(organization)

    @PutMapping
    fun updateOrganization(organization: OrganizationUpdate) = organizationService.updateOrganization(organization)
}