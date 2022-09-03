package pt.isel.ion.teams.notes

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Data Access Object for the Notes resource. This is an interface responsible for defining the methods for
 * accessing data of the given resource.
 */
interface NotesDAO {

    @SqlQuery("SELECT * FROM notes_view WHERE tid = :tId LIMIT :limit OFFSET :offset")
    fun getAllNotesFromTeam(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("tId") tId: Int
    ): List<NotesDbRead>

    @SqlQuery("SELECT * FROM notes_view WHERE id = :id")
    fun getNote(@Bind("id") id: Int): NotesDbRead

    @SqlUpdate("INSERT INTO notes (tid, description) VALUES (:tid,:description)")
    @GetGeneratedKeys
    fun createNote(@BindBean organization: NotesDbWrite): NotesDbRead

    @SqlUpdate("UPDATE notes SET description = :description WHERE id = :id")
    @GetGeneratedKeys
    fun updateNote(@BindBean organization: NotesDbUpdate): NotesDbRead

    @SqlUpdate("UPDATE notes SET deleted = B'1' WHERE id = :id")
    fun deleteNote(@Bind("id") noteId: Int)
}