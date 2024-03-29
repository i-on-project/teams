package pt.isel.ion.teams.classrooms

/**
 * This file contains the data class definitions for the different representations of the Classroom resource.
 */

/**
 * For internal use only.
 */
data class ClassroomDbRead(
    val id: Int,
    val name: String,
    val description: String,
    val maxTeams: Int,
    val maxMembersPerTeam: Int,
    val schoolYear: String,
    val orgId: Int,
    val state: String,
)

data class ClassroomDbWrite(
    val name: String,
    val description: String,
    val maxTeams: Int,
    val maxMembersPerTeam: Int,
    val schoolYear: String,
    val orgId: Int,
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

fun ClassroomInputModel.toDb(orgId: Int) =
    ClassroomDbWrite(
        this.name,
        this.description,
        this.maxTeams,
        this.maxMembersPerTeam,
        this.schoolYear,
        orgId,
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

