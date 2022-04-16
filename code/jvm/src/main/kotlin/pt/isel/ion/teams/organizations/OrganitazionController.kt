package pt.isel.ion.teams.organizations

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Organizations.PATH)
class OrganizationController(val organizationService: OrganizationService) {

    @GetMapping
    fun getAllOrganizations(): List<OrganizationOutputModel> =
        organizationService.getAllOrganizations().map { it.toOutput() }

    @GetMapping(Uris.Organizations.SingleOrganization.PATH)
    fun getOrganization(@PathVariable id: Int): OrganizationOutputModel = organizationService.getOrganization(id).toOutput()

    @PostMapping
    fun createOrganization(@RequestBody organization: OrganizationInputModel) =
        organizationService.createOrganization(organization.toDb())


    @PutMapping(Uris.Organizations.SingleOrganization.PATH)
    fun updateOrganization(@PathVariable id: Int, @RequestBody organization: OrganizationUpdateModel) =
        organizationService.updateOrganization(organization.toDb(id))
}