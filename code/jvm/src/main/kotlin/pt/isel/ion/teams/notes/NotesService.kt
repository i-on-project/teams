package pt.isel.ion.teams.notes

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class NotesService(val jdbi: Jdbi) {

    fun getAllNotesByTeam(teamId: Int) =
        jdbi.onDemand(NotesDAO::class.java).getAllNotes(teamId)

    fun getNote(id: Int) =
        jdbi.onDemand(NotesDAO::class.java).getNote(id)

    fun createNote(notesDbWrite: NotesDbWrite) =
        jdbi.onDemand(NotesDAO::class.java).createNote(notesDbWrite)

    fun updateNote(notesDbUpdate: NotesDbUpdate) =
        jdbi.onDemand(NotesDAO::class.java).updateNote(notesDbUpdate)
}