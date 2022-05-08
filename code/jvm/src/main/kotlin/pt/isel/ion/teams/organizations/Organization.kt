package pt.isel.ion.teams.organizations

/**
 * For internal use only.
 */
data class OrganizationDbRead(
    val id: Int,
    val name: String,
    val description: String,
    val githubUri: String,
    val avatarUri: String,
)

data class OrganizationDbWrite(
    val name: String,
    val description: String,
    val githubUri: String,
    val avatarUri: String,
)

data class OrganizationDbUpdate(
    val id: Int,
    val name: String?,
    val description: String?
)

/**
 * For external use only.
 */

data class OrganizationOutputModel(
    val id: Int,
    val name: String,
    val description: String,
    val githubUri: String,
    val avatarUri: String,
)

data class OrganizationCompactOutputModel(
    val id: Int,
    val name: String,
    val description: String
)

data class OrganizationInputModel(
    val name: String,
    val description: String
)

data class OrganizationUpdateModel(
    val name: String?,
    val description: String?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun OrganizationInputModel.toDb(githubUri: String, avatarUri: String) =
    OrganizationDbWrite(this.name, this.description, githubUri, avatarUri)

fun OrganizationUpdateModel.toDb(id: Int) = OrganizationDbUpdate(id, this.name, this.description)

fun OrganizationOutputModel.toCompactOutput() = OrganizationCompactOutputModel(this.id, this.name, this.description)

fun OrganizationDbRead.toCompactOutput() = OrganizationCompactOutputModel(this.id, this.name, this.description)

fun OrganizationDbRead.toOutput() =
    OrganizationOutputModel(this.id, this.name, this.description, this.githubUri, this.avatarUri)
