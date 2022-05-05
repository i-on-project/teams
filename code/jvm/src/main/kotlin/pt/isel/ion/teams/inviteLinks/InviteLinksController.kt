package pt.isel.ion.teams.inviteLinks

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.InviteLinks.MAIN_PATH)
class InviteLinksController(val service: InviteLinksService) {

    @GetMapping(Uris.InviteLinks.InviteLink.PATH)
    fun getInviteLink(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
    ): ResponseEntity<Any> {
        val invite_link = service.getInviteLink(classId).toOutput()

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(invite_link.toSirenObject(orgId, classId))
    }

    @PostMapping
    fun createInviteLink(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
    ): ResponseEntity<Any> {
        val invite = service.createInviteLink(classId)

        return ResponseEntity
            .created(Uris.InviteLinks.InviteLink.make(orgId, classId, invite.code))
            .contentType(MediaType.APPLICATION_JSON)
            .body(invite)
    }

    @DeleteMapping
    fun deleteInviteLink(@PathVariable classId: Int) {
        service.deleteInviteLink(classId)
    }

}