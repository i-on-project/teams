package pt.isel.ion.teams.inviteLinks

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.InviteLinks.MAIN_PATH)
class InviteLinksController(val service: InviteLinksService) {

    @GetMapping
    fun getAllInviteLinks(
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

    @GetMapping(Uris.InviteLinks.InviteLink.PATH)
    fun getInviteLink(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable code: String,
    ): ResponseEntity<Any> {
        val inviteLink = service.getInviteLink(classId, code).toOutput()

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(inviteLink.toSirenObject(orgId, classId))
    }

    @PostMapping
    fun createInviteLink(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
    ): ResponseEntity<Any> {
        val invite = service.createInviteLink(classId).toOutput()

        return ResponseEntity
            .created(Uris.InviteLinks.InviteLink.make(orgId, classId, invite.code))
            .contentType(MediaType.APPLICATION_JSON)
            .body(invite)
    }

    @DeleteMapping(Uris.InviteLinks.InviteLink.PATH)
    fun deleteInviteLink(@PathVariable classId: Int,@PathVariable code: String): ResponseEntity<Any> {
        service.deleteInviteLink(classId, code)

        return ResponseEntity
            .ok()
            .body(null)
    }

}