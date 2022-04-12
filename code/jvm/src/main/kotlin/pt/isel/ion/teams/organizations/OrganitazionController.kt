package pt.isel.ion.teams.organizations

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Organizations.PATH)
class OrganizationController(val organizationService: OrganizationService) {

    @GetMapping
    fun getAllOrganizations(): List<OrganizationOutput> =
        organizationService.getAllOrganizations().map { it.toOutput() }

    @GetMapping(Uris.Organizations.SingleOrganization.PATH)
    fun getOrganization(@PathVariable id: Int): OrganizationOutput = organizationService.getOrganization(id).toOutput()

    @PostMapping
    fun createOrganization(organization: OrganizationInput)/*: OrganizationOutput*/ =
        organizationService.createOrganization(organization.toDb())//.toOutput()

    @PutMapping
    fun updateOrganization(organization: OrganizationUpdate) =
        organizationService.updateOrganization(organization.toDb())
}