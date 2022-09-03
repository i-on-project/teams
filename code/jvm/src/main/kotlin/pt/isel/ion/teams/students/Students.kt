package pt.isel.ion.teams.students

/**
 * This file contains the data class definitions for the different representations of the Student resource.
 */

/**
 * For internal use only.
 */

data class CompleteStudentDbRead(
    val number: Int,
    val name: String,
    val githubusername: String?,
    val tid: Int,
    val cid: Int
)

data class StudentInfoDbRead(
    val number: Int,
    val name: String,
    val githubusername: String?
)

data class StudentClassInfoDbRead(
    val number: Int,
    val tid: Int,
    val cid: Int
)

data class StudentClassInfoDbWrite(
    val number: Int,
    val tid: Int,
    val cid: Int
)
data class StudentDbWrite(
    val number: Int,
    val name: String
)

data class StudentDbUpdate(
    val number: Int,
    val name: String? = null,
    val githubusername: String? = null
)

data class StudentTeamsDbRead(
    val orgName: String,
    val orgId: Int,
    val className: String,
    val classId: Int,
    val teamName: String,
    val teamId: Int,
    val number: Int
)

/**
 * For external use only.
 */

data class StudentInputModel(
    val number: Int,
    val name: String
)

data class StudentClassInfoInputModel(
    val number: Int
)

data class CompleteStudentOutputModel(
    val number: Int,
    val name: String,
    val tid: Int
)

data class StudentInfoOutputModel(
    val number: Int,
    val name: String
)

data class StudentClassInfoOutputModel(
    val number: Int,
    val tid: Int,
    val cid: Int
)

data class StudentCompactOutputModel(
    val number: Int,
    val name: String,
)

data class StudentUpdateModel(
    val name: String?,
    val githubusername: String?
)

data class StudentTeamsOutputModel(
    val orgName: String,
    val orgId: Int,
    val className: String,
    val classId: Int,
    val teamName: String,
    val teamId: Int,
    val number: Int
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun StudentClassInfoInputModel.toDb(tid: Int, cid: Int) = StudentClassInfoDbWrite(this.number, tid, cid)
fun StudentInputModel.toDb() = StudentDbWrite(this.number, this.name)
fun StudentInfoDbRead.toOutput() = StudentInfoOutputModel(this.number, this.name)
fun StudentClassInfoDbRead.toOutput() = StudentClassInfoOutputModel(this.number, this.tid, this.cid)
fun CompleteStudentDbRead.toOutput() = CompleteStudentOutputModel(this.number, this.name, this.tid)
fun CompleteStudentDbRead.toCompactOutput() =StudentCompactOutputModel(this.number,this.name)
fun StudentUpdateModel.toDb(number: Int) = StudentDbUpdate(number, this.name, this.githubusername)
fun StudentTeamsDbRead.toOutput() = StudentTeamsOutputModel(orgName, orgId, className, classId, teamName, teamId, number)
