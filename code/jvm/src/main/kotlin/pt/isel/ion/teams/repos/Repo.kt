package pt.isel.ion.teams.repos


/**
 * For internal use only.
 */
data class RepoDbRead (
    val id: Int,
    val url: String,
    val name: String,
    val teamId: Int,
    val assignmentId: Int
)

data class RepoDbWrite(
    val url: String,
    val name: String,
    val teamId: Int,
    val assignmentId: Int,
)

data class RepoDbUpdate(
    val id: Int,
    val name: String?,
    val teamId: Int?,
    val assignmentId: Int?,
)

/**
 * For external use only.
 */

data class RepoOutputModel(
    val id: Int,
    val url: String,
    val name: String,
    val teamId: Int,
    val assignmentId: Int
)

data class RepoInputModel(
    val url: String,
    val name: String,
    val teamId: Int,
    val assignmentId: Int,
)

data class RepoUpdateModel(
    val name: String?,
    val teamId: Int?,
    val assignmentId: Int?,
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun RepoInputModel.toDb() = RepoDbWrite(this.name,this.url,this.teamId,this.assignmentId)
fun RepoUpdateModel.toDb(id: Int) = RepoDbUpdate(id,this.name,this.teamId,this.assignmentId)
fun RepoDbRead.toOutput() = RepoOutputModel(this.id,this.name,this.url,this.teamId,this.assignmentId)
