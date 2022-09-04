package pt.isel.ion.teams.repos

/**
 * This file contains the data class definitions for the different representations of the Repo resource.
 */

/**
 * For internal use only.
 */
data class RepoDbRead (
    val id: Int,
    val url: String,
    val name: String,
    val tid: Int,
    val assId: Int
)

data class RepoDbWrite(
    val url: String,
    val name: String,
    val tid: Int,
    val assId: Int
)

data class RepoDbUpdate(
    val id: Int,
    val name: String?,
    val state: String?,
    val tid: Int?,
    val assId: Int?
)

/**
 * For external use only.
 */

data class RepoOutputModel(
    val id: Int,
    val url: String,
    val name: String,
    val tid: Int,
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
    val assId: Int
)

data class RepoUpdateModel(
    val name: String?,
    val state: String?,
    val tid: Int?,
    val assId: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun RepoInputModel.toDb(tId: Int) = RepoDbWrite(this.url, this.name, tId, this.assId)
fun RepoUpdateModel.toDb(id: Int) = RepoDbUpdate(id, this.name, this.state, this.tid, this.assId)
fun RepoDbRead.toOutput() = RepoOutputModel(this.id, this.url, this.name, this.assId, this.tid)
fun RepoDbRead.toCompactOutput() = RepoCompactOutputModel(this.id, this.url, this.name)
