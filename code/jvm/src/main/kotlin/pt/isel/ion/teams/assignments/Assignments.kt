package pt.isel.ion.teams.assignments

import java.sql.Timestamp

/**
 * For internal use only.
 */
data class AssignmentDbRead(
    val id: Int,
    val releaseDate: String,
    val description: String,
    val cid: Int,
)

data class AssignmentDbWrite(
    val releaseDate: Timestamp?,
    val cid: Int,
    val description: String,
)

data class AssignmentDbUpdate(
    val id: Int,
    val releaseDate: Timestamp?,
    val description: String?,
    val cid: Int?
)

/**
 * For external use only.
 */

data class AssignmentOutputModel(
    val id: Int,
    val releaseDate: String,
    val cid: Int,
    val description: String,
)

data class AssignmentCompactOutputModel(
    val id: Int,
    val releaseDate: String
)

data class AssignmentInputModel(
    val releaseDate: String?,
    val description: String
)

data class AssignmentUpdateModel(
    val releaseDate: String?,
    val description: String?,
    val cid: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun AssignmentInputModel.toDb(classId: Int) =
    AssignmentDbWrite(Timestamp.valueOf(this.releaseDate), classId, this.description)

fun AssignmentUpdateModel.toDb(id: Int) =
    AssignmentDbUpdate(id, Timestamp.valueOf(this.releaseDate), this.description, this.cid)

fun AssignmentDbRead.toOutput() = AssignmentOutputModel(this.id, this.releaseDate, this.cid, this.description)
fun AssignmentDbRead.toCompactOutput() = AssignmentCompactOutputModel(this.id, this.releaseDate)