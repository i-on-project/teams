package pt.isel.ion.teams.notes

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class NotesService(val jdbi: Jdbi) {

    fun getAllNotesByTeam(pageSize: Int, pageIndex: Int, teamId: Int) =
        sqlExceptionHandler {
            jdbi
                .onDemand(NotesDAO::class.java)
                .getAllNotesFromTeam(pageSize + 1, pageIndex * pageSize, teamId)
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

    fun deleteNote(noteId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(NotesDAO::class.java).deleteNote(noteId)
        }
}