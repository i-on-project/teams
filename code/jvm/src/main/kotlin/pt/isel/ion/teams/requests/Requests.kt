package pt.isel.ion.teams.requests

/**
 * This file contains the data class definitions for the different representations of the Request resource.
 */

/**
 * For internal use only.
 */
data class RequestsDbRead(
    val tid: Int,
    val teamName: String,
    val cid: Int
)

data class RequestsDbWrite(
    val tid: Int,
    val teamName: String,
    val cid: Int
)

data class RequestsDbUpdate(
    val tid: Int,
    val teamName: String?,
    val cid: Int?
)

/**
 * For external use only.
 */

data class RequestsOutputModel(
    val tid: Int,
    val teamName: String,
    val cid: Int
)

data class RequestsCompactOutputModel(
    val teamName: String,
    val cid: Int
)

data class RequestsInputModel(
    val tid: Int,
    val teamName: String,
    val cid: Int
)

data class RequestsUpdateModel(
    val tid: Int,
    val teamName: String?,
    val cid: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun RequestsInputModel.toDb() = RequestsDbWrite(this.cid, this.teamName, this.cid)
fun RequestsDbRead.toOutput() = RequestsOutputModel(this.tid, this.teamName, this.cid)
fun RequestsDbRead.toCompactOutput() = RequestsCompactOutputModel(this.teamName, this.cid)
fun RequestsOutputModel.toCompactOutput() = RequestsCompactOutputModel(this.teamName, this.cid)

