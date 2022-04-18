package pt.isel.ion.teams.notes

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Notes.PATH)
class NotesController (val notesService: NotesService) {

    @GetMapping
    fun getAllNotesByTeam(@PathVariable teamId: Int): List<NotesOutputModel> =
        notesService.getAllNotesByTeam(teamId).map { it.toOutput() }

    @GetMapping(Uris.Notes.Note.PATH)
    fun getNotes(@PathVariable id: Int): NotesOutputModel =
        notesService.getNote(id).toOutput()

    @PostMapping
    fun createNote(@PathVariable teamId: Int, @RequestBody notesInputModel: NotesInputModel) =
        notesService.createNote(notesInputModel.toDb(teamId))

    @PutMapping
    fun updateNote(@PathVariable id: Int, @RequestBody notesUpdateModel: NotesUpdateModel) =
        notesService.updateNote(notesUpdateModel.toDb(id))
}