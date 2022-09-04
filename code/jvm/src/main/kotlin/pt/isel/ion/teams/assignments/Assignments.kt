package pt.isel.ion.teams.assignments

import java.sql.Timestamp

/**
 * This file contains the data class definitions for the different representations of the Assignment resource.
 */

/**
 * For internal use only.
 */
data class AssignmentDbRead(
    val id: Int,
    val releaseDate: String,
    val description: String,
    val name: String,
    val cid: Int,
)

data class AssignmentDbWrite(
    val releaseDate: Timestamp?,
    val cid: Int,
    val description: String,
    val name: String
)

data class AssignmentDbUpdate(
    val id: Int,
    val releaseDate: Timestamp?,
    val description: String?,
    val name: String,
    val cid: Int?
)

/**
 * For external use only.
 */

data class AssignmentOutputModel(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val cid: Int,
    val description: String,
)

data class AssignmentCompactOutputModel(
    val id: Int,
    val name: String,
    val releaseDate: String
)

data class AssignmentInputModel(
    var releaseDate: String?,
    val description: String,
    val name: String
)

data class AssignmentUpdateModel(
    var releaseDate: String?,
    val name: String,
    val description: String?,
    val cid: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun AssignmentInputModel.toDb(classId: Int) =
    AssignmentDbWrite(Timestamp.valueOf(this.releaseDate), classId, this.description, this.name)

fun AssignmentUpdateModel.toDb(id: Int) =
    AssignmentDbUpdate(id, Timestamp.valueOf(this.releaseDate), this.description, this.name, this.cid)

fun AssignmentDbRead.toOutput() = AssignmentOutputModel(this.id, this.name, this.releaseDate, this.cid, this.description)
fun AssignmentDbRead.toCompactOutput() = AssignmentCompactOutputModel(this.id, this.name, this.releaseDate)