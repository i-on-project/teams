package pt.isel.ion.teams.teacher

/**
 * This file contains the data class definitions for the different representations of the Teacher resource.
 */

/**
 * For internal use only.
 */
data class CompleteTeacherDbRead(
    val number: Int,
    val name: String,
    val email: String,
    val office: String,
    val cid: Int,
    val orgid: Int
)

data class InfoTeacherDbRead(
    val number: Int,
    val name: String,
    val githubusername: String?,
    val email: String,
    val office: String
)

data class SimpleTeacherDbRead(
    val number: Int,
    val cid: Int?,
    val orgid: Int
)

data class TeacherDbWrite(
    val number: Int,
    val name: String,
    val email: String,
    val office: String
)

data class TeacherDbUpdate(
    val number: Int,
    val name: String? = null,
    val email: String? = null,
    val office: String? = null,
    val githubusername: String? = null,
    val cid: Int? = null,
    val orgid: Int? = null
)

/**
 * For external use only.
 */

data class CompleteTeacherOutputModel(
    val number: Int,
    val name: String,
    val email: String,
    val office: String,
    val cid: Int,
    val orgid: Int
)

data class SimpleTeacherOutputModel(
    val number: Int,
    val cid: Int?,
    val orgid: Int
)

data class TeacherCompactOutputModel(
    val number: Int,
    val name: String,
    val email: String,
    val office: String
)

data class TeacherInputModel(
    val number: Int,
    val name: String,
    val email: String,
    val office: String
)

data class TeacherUpdateModel(
    val name: String?,
    val email: String?,
    val office: String?,
    val githubusername: String?,
    val cid: Int?,
    val orgid: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */
fun TeacherInputModel.toDb() = TeacherDbWrite(this.number, this.name, this.email, this.office)
fun TeacherUpdateModel.toDb(number: Int) = TeacherDbUpdate(number, this.name, this.email, this.office, this.githubusername, this.cid, this.orgid)
fun CompleteTeacherDbRead.toOutput() = CompleteTeacherOutputModel(this.number, this.name, this.email, this.office, this.cid, this.orgid)
fun SimpleTeacherDbRead.toOutput() = SimpleTeacherOutputModel(this.number, this.cid, this.orgid)
fun InfoTeacherDbRead.toOutput() = TeacherCompactOutputModel(this.number, this.name, this.email, this.office)
fun CompleteTeacherDbRead.toCompactOutput() = TeacherCompactOutputModel(this.number, this.name, this.email, this.office)
