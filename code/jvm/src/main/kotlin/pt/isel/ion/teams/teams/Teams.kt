package pt.isel.ion.teams.teams

/**
 * For internal use only.
 */
data class TeamsDbRead (
    val id: Int,
    val name: String,
    val cid: Int,
    val state: String
)

data class TeamsDbWrite(
    val name: String,
    val cid: Int,
    val state: String?
)

data class TeamsDbUpdate(
    val id: Int,
    val name: String?,
    val state: String?
)

/**
 * For external use only.
 */

data class TeamsOutputModel(
    val id: Int,
    val name: String,
    val cid: Int,
    val state: String
)

data class TeamsCompactOutputModel(
    val id: Int,
    val name: String,
    val state: String
)

data class TeamsInputModel(
    val name: String,
    val state: String?
)

data class TeamsUpdateModel(
    val name: String?,
    val state: String?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun TeamsInputModel.toDb(cId: Int) = TeamsDbWrite(this.name, cId, this.state)
fun TeamsUpdateModel.toDb(id: Int) = TeamsDbUpdate(id, this.name, this.state)
fun TeamsDbRead.toOutput() = TeamsOutputModel(this.id, this.name, this.cid, this.state)
fun TeamsDbRead.toCompactOutput() = TeamsCompactOutputModel(this.id, this.name, this.state)
