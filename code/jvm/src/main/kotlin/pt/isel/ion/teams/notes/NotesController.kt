package pt.isel.ion.teams.notes

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.CollectionModel
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.teams.*

@RestController
@RequestMapping(Uris.Notes.MAIN_PATH)
class NotesController (val notesService: NotesService) {

    @GetMapping
    fun getAllNotesOfTeam(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize)
                .toNotesSirenObject(
                    notesService.getAllNotesByTeam(pageSize, pageIndex, teamId).map { it.toCompactOutput() },
                    orgId,
                    classId,
                    teamId
                )
        )

    @GetMapping(Uris.Notes.Note.PATH)
    fun getNote(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int,
        @PathVariable noteId: Int
    ): ResponseEntity<Any> {
        val note = notesService.getNote(noteId).toOutput()

        //TODO Detect if user is student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(note.toSirenObject(orgId, classId, teamId))
    }

    @PostMapping
    fun createNote(
        @RequestBody note: NotesInputModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int,
    ): ResponseEntity<Any> {
        val createdNote = notesService.createNote(note.toDb(teamId))

        return ResponseEntity
            .created(Uris.Notes.Note.make(orgId, classId, teamId, createdNote.id))
            .contentType(MediaType.APPLICATION_JSON)
            .body(createdNote)
    }

    @PutMapping(Uris.Notes.Note.PATH)
    fun updateTeam(
        @RequestBody note: NotesUpdateModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int,
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(notesService.updateNote(note.toDb(teamId)).toOutput())

    @DeleteMapping(Uris.Notes.Note.PATH)
    fun deleteTeam(@PathVariable noteId: Int): ResponseEntity<Any> {
        notesService.deleteNote(noteId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}