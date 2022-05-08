package pt.isel.ion.teams.tags

import java.util.Date


/**
 * For internal use only.
 */
data class TagDbRead(
    val id: Int,
    val name: String,
    val date: String,
    val delId: Int,
    val repoId: Int
)

data class TagWithTeamRepoDbRead(
    val id: Int,
    val name: String,
    val date: String,
    val repoId: Int,
    val teamId: Int,
    val delId: Int
)

data class TagDbWrite(
    val name: String,
    val delId: Int,
    val repoId: Int
)

data class TagDbUpdate(
    val id: Int,
    val name: String?,
    val delId: Int?,
    val repoId: Int?
)

/**
 * For external use only.
 */

data class TagOutputModel(
    val id: Int,
    val name: String,
    val date: String,
)

data class TagCompactOutputModel(
    val id: Int,
    val name: String,
    val date: String,
)

data class TagInputModel(
    val name: String,
    val delId: Int,
    val repoId: Int
)

data class TagUpdateModel(
    val name: String?,
    val delId: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun TagInputModel.toDb(repoId: Int) = TagDbWrite(this.name, this.delId, repoId)
fun TagUpdateModel.toDb(id: Int,repoId: Int) = TagDbUpdate(id, this.name, this.delId, repoId)
fun TagDbRead.toOutput() = TagOutputModel(this.id, this.name, this.date)
fun TagDbRead.toCompactOutput() = TagCompactOutputModel(this.id, this.name, this.date)
