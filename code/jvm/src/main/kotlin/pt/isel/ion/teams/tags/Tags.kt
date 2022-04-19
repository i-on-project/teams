package pt.isel.ion.teams.tags

import java.util.Date


/**
 * For internal use only.
 */
data class TagDbRead(
    val id: Int,
    val name: String,
    val date: Date,
    val delId: Int,
    val teamId: Int
)

data class TagDbWrite(
    val name: String,
    val date: Date,
    val delId: Int,
    val teamId: Int
)

data class TagDbUpdate(
    val id: Int,
    val name: String?,
    val date: Date?,
    val delId: Int?,
    val teamId: Int?
)

/**
 * For external use only.
 */

data class TagOutputModel(
    val id: Int,
    val name: String,
    val date: Date,
    val delId: Int,
    val teamId: Int
)

data class TagInputModel(
    val name: String,
    val date: Date,
    val delId: Int,
    val teamId: Int
)

data class TagUpdateModel(
    val name: String?,
    val date: Date?,
    val delId: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun TagInputModel.toDb(teamId: Int) = TagDbWrite(this.name,this.date,this.delId,teamId)
fun TagUpdateModel.toDb(id: Int,teamId: Int) = TagDbUpdate(id,this.name,this.date,this.delId,teamId)
fun TagDbRead.toOutput() = TagOutputModel(this.id, this.name,this.date,this.delId,this.teamId)
