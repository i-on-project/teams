package pt.isel.ion.teams.inviteCode

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.classrooms.ClassroomsService
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.InviteCodes.MAIN_PATH)
class InviteCodeController(
    val service: InviteCodeService,
    val classroomsService: ClassroomsService
    ) {

    @GetMapping
    fun getAllInviteCodes(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
    ): ResponseEntity<Any> =
        ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                CollectionModel(pageIndex, pageSize).toInviteLinksSirenObject(
                    service.getAllInviteLinks(pageSize, pageIndex, classId).map { it.toOutput() },
                    orgId,
                    classId
                )
            )

    @GetMapping(Uris.InviteCodes.InviteCode.PATH)
    fun getInviteCode(
        @PathVariable code: String,
    ): ResponseEntity<Any> {
        val inviteLink = service.getInviteLink(code).toOutput()
        val orgId = classroomsService.getClassroom(inviteLink.cid).orgId

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(inviteLink.toSirenObject(orgId))
    }

    @PostMapping
    fun createInviteCode(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
    ): ResponseEntity<Any> {
        val invite = service.createInviteLink(classId).toOutput()

        return ResponseEntity
            .created(Uris.InviteCodes.InviteCode.make(invite.code))
            .contentType(MediaType.APPLICATION_JSON)
            .body(invite)
    }

    @DeleteMapping(Uris.InviteCodes.InviteCode.PATH)
    fun deleteInviteCode(@PathVariable code: String): ResponseEntity<Any> {
        service.deleteInviteLink(code)

        return ResponseEntity
            .ok()
            .body(null)
    }

}