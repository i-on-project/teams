package pt.isel.ion.teams.organizations

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Organizations.PATH)
class OrganizationController(val organizationService: OrganizationService) {

    @GetMapping
    fun getAllOrganizations(): List<OrganizationOutputModel> =
        organizationService.getAllOrganizations().map { it.toOutput() }

    @GetMapping(Uris.Organizations.Organization.PATH)
    fun getOrganization(@PathVariable orgId: Int): OrganizationOutputModel =
        organizationService.getOrganization(orgId).toOutput()

    @PostMapping
    fun createOrganization(@RequestBody organization: OrganizationInputModel) =
        organizationService.createOrganization(organization.toDb())

    @PutMapping(Uris.Organizations.Organization.PATH)
    fun updateOrganization(@PathVariable orgId: Int, @RequestBody organization: OrganizationUpdateModel) =
        organizationService.updateOrganization(organization.toDb(orgId))
}