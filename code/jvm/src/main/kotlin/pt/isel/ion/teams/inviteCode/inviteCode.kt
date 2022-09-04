package pt.isel.ion.teams.inviteCode

/**
 * This file contains the data class definitions for the different representations of the Invite-code resource.
 */

/**
 * For internal use only.
 */
data class InviteCodesDbRead(
    val code: String,
    val cId: Int
)

data class InviteCodesDbWrite(
    val code: String,
    val cId: Int
)

data class InviteCodesDbUpdate(
    val code: String,
    val cId: Int
)

/**
 * For external use only.
 */

data class InviteCodesOutputModel(
    val code: String,
    val cid: Int
)

data class InviteCodesCompactOutputModel(
    val code: String,
    val cId: Int
)

data class InviteCodesInputModel(
    val code: String,
    val cId: Int
)

data class InviteCodesUpdateModel(
    val code: String,
    val cId: Int
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun InviteCodesInputModel.toDb() =
    InviteCodesDbWrite(this.code, this.cId)
fun InviteCodesUpdateModel.toDb() =
    InviteCodesDbUpdate(this.code, this.cId)
fun InviteCodesDbRead.toOutput() = InviteCodesOutputModel(this.code, this.cId)
fun InviteCodesDbRead.toCompactOutput() = InviteCodesCompactOutputModel(this.code, this.cId)