package pt.isel.ion.teams.inviteLinks

/**
 * For internal use only.
 */
data class InviteLinksDbRead(
    val code: String,
    val classId: Int
)

data class InviteLinksDbWrite(
    val code: String,
    val classId: Int
)

data class InviteLinksDbUpdate(
    val code: String,
    val classId: Int
)

/**
 * For external use only.
 */

data class InviteLinksOutputModel(
    val code: String,
    val classId: Int
)

data class InviteLinksCompactOutputModel(
    val code: String,
    val classId: Int
)

data class InviteLinksInputModel(
    val code: String,
    val classId: Int
)

data class InviteLinksUpdateModel(
    val code: String,
    val classId: Int
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun InviteLinksInputModel.toDb() =
    InviteLinksDbWrite(this.code, this.classId)
fun InviteLinksUpdateModel.toDb() =
    InviteLinksDbUpdate(this.code, this.classId)
fun InviteLinksDbRead.toOutput() = InviteLinksOutputModel(this.code, this.classId)
fun InviteLinksDbRead.toCompactOutput() = InviteLinksCompactOutputModel(this.code, this.classId)