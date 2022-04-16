package pt.isel.ion.teams.organizations

import org.springframework.web.bind.annotation.*
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
    fun createOrganization(@RequestBody organization: OrganizationInput) =
        organizationService.createOrganization(organization.toDb())


    @PutMapping(Uris.Organizations.SingleOrganization.PATH)
    fun updateOrganization(@PathVariable id: Int, @RequestBody organization: OrganizationUpdate) =
        organizationService.updateOrganization(organization.toDb(id))
}