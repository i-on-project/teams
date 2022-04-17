package pt.isel.ion.teams.assignments

import java.sql.Timestamp

/**
 * For internal use only.
 */
data class AssignmentDbRead (
    val id: Int,
    val releaseDate: Timestamp,
    val classId: Int,
    val description: String,
)

data class AssignmentDbWrite(
    val releaseDate: Timestamp,
    val classId: Int,
    val description: String,
)

data class AssignmentDbUpdate(
    val id: Int,
    val releaseDate: Timestamp?,
    val description: String?,
)

/**
 * For external use only.
 */

data class AssignmentOutputModel(
    val id: Int,
    val releaseDate: Timestamp,
    val classId: Int,
    val description: String,
)

data class AssignmentInputModel(
    val releaseDate: Timestamp,
    val classId: Int,
    val description: String,
)

data class AssignmentUpdateModel(
    val id: Int,
    val releaseDate: Timestamp?,
    val description: String?,
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun AssignmentInputModel.toDb() =
    AssignmentDbWrite(this.releaseDate,this.classId,this.description)
fun AssignmentUpdateModel.toDb(id: Int) =
    AssignmentDbUpdate(id,this.releaseDate,this.description)
fun AssignmentDbRead.toOutput() = AssignmentOutputModel(this.id,this.releaseDate,this.classId,this.description)

