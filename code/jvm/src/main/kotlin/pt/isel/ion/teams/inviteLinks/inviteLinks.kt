package pt.isel.ion.teams.inviteLinks

/**
 * For internal use only.
 */
data class InviteLinksDbRead(
    val code: String,
    val cId: Int
)

data class InviteLinksDbWrite(
    val code: String,
    val cId: Int
)

data class InviteLinksDbUpdate(
    val code: String,
    val cId: Int
)

/**
 * For external use only.
 */

data class InviteLinksOutputModel(
    val code: String,
    val cId: Int
)

data class InviteLinksCompactOutputModel(
    val code: String,
    val cId: Int
)

data class InviteLinksInputModel(
    val code: String,
    val cId: Int
)

data class InviteLinksUpdateModel(
    val code: String,
    val cId: Int
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun InviteLinksInputModel.toDb() =
    InviteLinksDbWrite(this.code, this.cId)
fun InviteLinksUpdateModel.toDb() =
    InviteLinksDbUpdate(this.code, this.cId)
fun InviteLinksDbRead.toOutput() = InviteLinksOutputModel(this.code, this.cId)
fun InviteLinksDbRead.toCompactOutput() = InviteLinksCompactOutputModel(this.code, this.cId)