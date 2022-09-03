package pt.isel.ion.teams.notes

/**
 * This file contains the data class definitions for the different representations of the Note resource.
 */

/**
 * For internal use only.
 */
data class NotesDbRead(
    val id: Int,
    val tid: Int,
    val date: String,
    val description: String,
)

data class NotesDbWrite(
    val tid: Int,
    val description: String,
)

data class NotesDbUpdate(
    val id: Int,
    val description: String,
)

/**
 * For external use only.
 */

data class NotesOutputModel(
    val id: Int,
    val date: String,
    val description: String,
)

data class NotesCompactOutputModel(
    val id: Int,
    val date: String,
)

data class NotesInputModel(
    val description: String
)

data class NotesUpdateModel(
    val description: String
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun NotesInputModel.toDb(teamId: Int) = NotesDbWrite(teamId, this.description)
fun NotesUpdateModel.toDb(id: Int) = NotesDbUpdate(id, this.description)
fun NotesDbRead.toOutput() = NotesOutputModel(this.id, this.date, this.description)
fun NotesDbRead.toCompactOutput() = NotesCompactOutputModel(this.id, this.date)