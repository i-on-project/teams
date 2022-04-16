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
    val classId: Int,
    val description: String,
)

data class AssignmentDbUpdate(
    val id: Int,
    val description: String?,
)

/**
 * For external use only.
 */

data class AssignmentOutputModel(
    val id: Int,
    val name: String,
    val description: String,
    val schoolYear: String,
    val state: String,
)

data class AssignmentInputModel(
    val name: String,
    val description: String,
    val maxGroups: Int,
    val maxGroupMembers: Int,
    val schoolYear: String,
)

data class AssignmentUpdateModel(
    val name: String?,
    val description: String?,
    val maxGroups: Int?,
    val maxGroupMembers: Int?,
    val state: String?,
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun AssignmentInputModel.toDb(repoURI: String, orgId: Int) =
    AssignmentDbWrite(this.name, this.description, this.maxGroups, this.maxGroupMembers, repoURI, this.schoolYear, orgId)
fun AssignmentUpdateModel.toDb(id: Int) =
    AssignmentDbUpdate(id, this.name, this.description, this.maxGroups, this.maxGroupMembers, this.state)
fun AssignmentDbRead.toOutput() = AssignmentOutputModel(this.id, this.name, this.description, this.schoolYear, this.state)

