package pt.isel.ion.teams.organizations

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.CollectionModel
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.classrooms.ClassroomService
import pt.isel.ion.teams.classrooms.toCompactOutput
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Organizations.MAIN_PATH)
class OrganizationController(
    val organizationService: OrganizationService,
    val classroomService: ClassroomService
) {

    @GetMapping
    fun getAllOrganizations(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize)
                .toOrganizationsSirenObject(
                    organizationService.getAllOrganizations(pageSize, pageIndex).map { it.toOutput() }
                )
        )

    @GetMapping(Uris.Organizations.Organization.PATH)
    fun getOrganization(@PathVariable orgId: Int): ResponseEntity<Any> {
        val org = organizationService.getOrganization(orgId).toOutput()
        val classrooms = classroomService.getAllClassroomsByOrganization(orgId)

        //TODO Detect if user is student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(org.toTeacherSirenObject(classrooms.map { it.toCompactOutput() }))
    }

    @PostMapping
    fun createOrganization(@RequestBody organization: OrganizationInputModel): ResponseEntity<Any> {
        val org = organizationService.createOrganization(organization.toDb())

        return ResponseEntity
            .created(Uris.Organizations.Organization.make(org.id))
            .contentType(MediaType.APPLICATION_JSON)
            .body(org)
    }

    @PutMapping(Uris.Organizations.Organization.PATH)
    fun updateOrganization(@PathVariable orgId: Int, @RequestBody organization: OrganizationUpdateModel) =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(organizationService.updateOrganization(organization.toDb(orgId)))
}