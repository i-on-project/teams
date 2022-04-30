package pt.isel.ion.teams.notes

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class NotesService(val jdbi: Jdbi) {

    fun getAllNotesByTeam(teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(NotesDAO::class.java).getAllNotes(teamId)
        }

    fun getNote(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(NotesDAO::class.java).getNote(id)
        }

    fun createNote(notesDbWrite: NotesDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(NotesDAO::class.java).createNote(notesDbWrite)
        }

    fun updateNote(notesDbUpdate: NotesDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(NotesDAO::class.java).updateNote(notesDbUpdate)
        }
}