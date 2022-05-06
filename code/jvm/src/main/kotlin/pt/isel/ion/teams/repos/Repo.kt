package pt.isel.ion.teams.repos


/**
 * For internal use only.
 */
data class RepoDbRead (
    val id: Int,
    val url: String,
    val name: String,
    val teamId: Int,
    val assId: Int
)

data class RepoDbWrite(
    val url: String,
    val name: String,
    val teamId: Int,
    val assId: Int,
)

data class RepoDbUpdate(
    val id: Int,
    val name: String?,
    val teamId: Int?,
    val assId: Int?,
)

/**
 * For external use only.
 */

data class RepoOutputModel(
    val id: Int,
    val url: String,
    val name: String,
    val assId: Int
)

data class RepoCompactOutputModel(
    val id: Int,
    val url: String,
    val name: String,
)

data class RepoInputModel(
    val url: String,
    val name: String,
    val teamId: Int,
    val assId: Int,
)

data class RepoUpdateModel(
    val name: String?,
    val teamId: Int?,
    val assId: Int?,
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun RepoInputModel.toDb() = RepoDbWrite(this.name, this.url, this.teamId, this.assId)
fun RepoUpdateModel.toDb(id: Int) = RepoDbUpdate(id, this.name, this.teamId, this.assId)
fun RepoDbRead.toOutput() = RepoOutputModel(this.id, this.name, this.url, this.assId)
fun RepoDbRead.toCompactOutput() = RepoCompactOutputModel(this.id, this.name, this.url)
