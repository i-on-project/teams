package pt.isel.ion.teams.notes

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

/**
 * Service for the Note resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
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