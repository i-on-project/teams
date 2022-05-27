package pt.isel.ion.teams.organizations

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.classrooms.ClassroomsService
import pt.isel.ion.teams.classrooms.toCompactOutput
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Organizations.MAIN_PATH)
class OrganizationController(
    val organizationsService: OrganizationsService,
    val classroomsService: ClassroomsService
) {

    @GetMapping
    fun getAllOrganizations(
        @CookieValue("number") number: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize)
                .toOrganizationsSirenObject(
                    organizationsService.getAllOrganizationsOfTeacher(number, pageSize, pageIndex).map { it.toOutput() }
                )
        )

    @GetMapping(Uris.Organizations.Organization.PATH)
    fun getOrganization(@PathVariable orgId: Int): ResponseEntity<Any> {
        val org = organizationsService.getOrganization(orgId).toOutput()
        val classrooms = classroomsService.getAllClassroomsByOrganizationWithPaging(10,0,orgId,null)

        //TODO Detect if user is student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(org.toTeacherSirenObject(classrooms.map { it.toCompactOutput() }))
    }

    @PostMapping
    fun createOrganization(@RequestBody organization: OrganizationInputModel): ResponseEntity<Any> {
        //TODO retrieve real githubURI and avatarURI
        val org = organizationsService.createOrganization(organization.toDb("example","example")).toOutput()

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
            .body(organizationsService.updateOrganization(organization.toDb(orgId)).toOutput())

    @DeleteMapping(Uris.Organizations.Organization.PATH)
    fun deleteProject(@PathVariable("orgId") id: Int): ResponseEntity<Any> {
        organizationsService.deleteOrganization(id)

        return ResponseEntity
            .ok()
            .body(null)
    }
}