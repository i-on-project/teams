package pt.isel.ion.teams.classrooms

/**
 * For internal use only.
 */
data class ClassroomDbRead (
    val id: Int,
    val name: String,
    val description: String,
    val maxGroups: Int,
    val maxGroupMembers: Int,
    val repoURI: String,
    val schoolYear: String,
    val orgId: Int,
    val state: String,
)

data class ClassroomDbWrite(
    val name: String,
    val description: String,
    val maxGroups: Int,
    val maxGroupMembers: Int,
    val repoURI: String,
    val schoolYear: String,
    val orgId: Int,
)

data class ClassroomDbUpdate(
    val id: Int,
    val name: String?,
    val description: String?,
    val maxGroups: Int?,
    val maxGroupMembers: Int?,
    val state: String?,
    val schoolYear: String?,
)

/**
 * For external use only.
 */

data class ClassroomOutputModel(
    val id: Int,
    val name: String,
    val description: String,
    val schoolYear: String,
    val state: String,
)

data class ClassroomCompactOutputModel(
    val id: Int,
    val name: String,
    val description: String,
    val schoolYear: String,
)

data class ClassroomInputModel(
    val name: String,
    val description: String,
    val maxGroups: Int,
    val maxGroupMembers: Int,
    val repoURI: String,
    val schoolYear: String,
)

data class ClassroomUpdateModel(
    val id: Int,
    val name: String?,
    val description: String?,
    val maxGroups: Int?,
    val maxGroupMembers: Int?,
    val state: String?,
    val schoolYear: String?,
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun ClassroomInputModel.toDb(orgId: Int) =
    ClassroomDbWrite(this.name, this.description, this.maxGroups, this.maxGroupMembers, this.repoURI, this.schoolYear, orgId)
fun ClassroomUpdateModel.toDb() =
    ClassroomDbUpdate(this.id, this.name, this.description, this.maxGroups, this.maxGroupMembers, this.state,this.schoolYear)
fun ClassroomDbRead.toOutput() = ClassroomOutputModel(this.id, this.name, this.description, this.schoolYear, this.state)
fun ClassroomDbRead.toCompactOutput() = ClassroomCompactOutputModel(this.id, this.name, this.description, this.schoolYear)

