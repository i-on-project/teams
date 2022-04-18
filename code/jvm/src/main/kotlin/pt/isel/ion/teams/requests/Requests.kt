package pt.isel.ion.teams.requests

import java.time.ZoneId


/**
 * For internal use only.
 */
data class RequestsDbRead (
    val invite_link: String,
    val teamId: String
)

data class RequestsDbWrite(
    val invite_link: String,
    val teamId: String
)

data class RequestsDbUpdate(
    val invite_link: String,
    val teamId: String
)

/**
 * For external use only.
 */

data class RequestsOutputModel(
    val invite_link: String,
    val teamId: String
)

data class RequestsInputModel(
    val invite_link: String,
    val teamId: String
)

data class RequestsUpdateModel(
    val invite_link: String,
    val teamId: String
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun RequestsInputModel.toDb() = RequestsDbWrite(this.invite_link, this.teamId)
fun RequestsUpdateModel.toDb(id: Int) = RequestsDbUpdate(this.invite_link, this.teamId)
fun RequestsDbRead.toOutput() = RequestsOutputModel(this.invite_link, this.teamId)

