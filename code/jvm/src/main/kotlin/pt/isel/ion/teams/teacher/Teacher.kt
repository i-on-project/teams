package pt.isel.ion.teams.teacher

import java.util.*


/**
 * For internal use only.
 */
data class TeacherDbRead(
    val number: Int,
    val name: String,
    val email: String,
    val office: String
)

data class TeacherDbWrite(
    val number: Int,
    val name: String,
    val email: String,
    val office: String
)

data class TeacherDbUpdate(
    val number: Int,
    val name: String?,
    val email: String?,
    val office: String?
)

/**
 * For external use only.
 */

data class TeacherOutputModel(
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
    val office: String?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun TeacherInputModel.toDb() = TeacherDbWrite(this.number,this.name,this.email,this.office)
fun TeacherUpdateModel.toDb(number: Int) = TeacherDbUpdate(number,this.name,this.email,this.office)
fun TeacherDbRead.toOutput() = TeacherOutputModel(this.number,this.name,this.email,this.office)
