package pt.isel.ion.teams.classrooms

/**
 * For internal use only.
 */
data class ClassroomDbRead(
    val id: Int,
    val name: String,
    val description: String,
    val maxTeams: Int,
    val maxMembersPerTeam: Int,
    val repoURI: String,
    val schoolYear: String,
    val orgId: Int,
    val state: String,
    val githubURI: String,
    val avatarURI: String
)

data class ClassroomDbWrite(
    val name: String,
    val description: String,
    val maxTeams: Int,
    val maxMembersPerTeam: Int,
    val repoURI: String,
    val schoolYear: String,
    val orgId: Int,
    val githubURI: String,
    val avatarURI: String
)

data class ClassroomDbUpdate(
    val id: Int,
    val name: String?,
    val description: String?,
    val maxTeams: Int?,
    val maxMembersPerTeam: Int?,
    val state: String?,
    val schoolYear: String?,
)

/**
 * For external use only.
 */

data class ClassroomOutputModel(
    val id: Int,
    val name: String,
    val description: String,
    val schoolYear: String,
    val state: String,
    val githubURI: String,
    val avatarURI: String
)

data class SimpleClassroomOutputModel(
    val id: Int,
    val name: String,
    val description: String,
    val schoolYear: String,
    val state: String,
    val orgId: Int
)

data class ClassroomCompactOutputModel(
    val id: Int,
    val name: String,
    val description: String,
    val schoolYear: String,
)

data class ClassroomInputModel(
    val name: String,
    val description: String,
    val maxTeams: Int,
    val maxMembersPerTeam: Int,
    val repoURI: String,
    val schoolYear: String,
)

data class ClassroomUpdateModel(
    val name: String?,
    val description: String?,
    val maxTeams: Int?,
    val maxMembersPerTeam: Int?,
    val state: String?,
    val schoolYear: String?,
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun ClassroomInputModel.toDb(orgId: Int, githubUri: String, avatarUri: String) =
    ClassroomDbWrite(
        this.name,
        this.description,
        this.maxTeams,
        this.maxMembersPerTeam,
        this.repoURI,
        this.schoolYear,
        orgId,
        githubUri,
        avatarUri
    )

fun ClassroomUpdateModel.toDb(id: Int) =
    ClassroomDbUpdate(
        id,
        this.name,
        this.description,
        this.maxTeams,
        this.maxMembersPerTeam,
        this.state,
        this.schoolYear
    )

fun ClassroomDbRead.toOutput() =
    ClassroomOutputModel(
        this.id,
        this.name,
        this.description,
        this.schoolYear,
        this.state,
        this.githubURI,
        this.avatarURI
    )

fun ClassroomDbRead.toSimpleOutput() =
    SimpleClassroomOutputModel(
        this.id,
        this.name,
        this.description,
        this.schoolYear,
        this.state,
        this.orgId
    )

fun ClassroomDbRead.toCompactOutput() =
    ClassroomCompactOutputModel(this.id, this.name, this.description, this.schoolYear)

fun ClassroomOutputModel.toCompactOutput() =
    ClassroomCompactOutputModel(this.id, this.name, this.description, this.schoolYear)

fun SimpleClassroomOutputModel.toCompactOutput() =
    ClassroomCompactOutputModel(this.id, this.name, this.description, this.schoolYear)

