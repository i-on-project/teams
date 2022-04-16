package pt.isel.ion.teams.organizations

/**
 * For internal use only.
 */
data class OrganizationDbRead (
    val id: Int,
    val name: String,
    val description: String
)

data class OrganizationDbWrite(
    val name: String,
    val description: String
)

data class OrganizationDbUpdate(
    val id: Int,
    val name: String?,
    val description: String?
)

/**
 * For external use only.
 */

data class OrganizationOutput(
    val id: Int,
    val name: String,
    val description: String
)

data class OrganizationInput(
    val name: String,
    val description: String
)

data class OrganizationUpdate(
    val name: String?,
    val description: String?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun OrganizationInput.toDb() = OrganizationDbWrite(this.name, this.description)
fun OrganizationUpdate.toDb(id: Int) = OrganizationDbUpdate(id, this.name, this.description)
fun OrganizationDbRead.toOutput() = OrganizationOutput(this.id, this.name, this.description)
