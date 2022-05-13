package pt.isel.ion.teams.students

/**
 * For internal use only.
 */

data class CompleteStudentDbRead(
    val number: Int,
    val name: String,
    val tid: Int,
    val cid: Int
)

data class PersonalInfoStudentDbRead(
    val number: Int,
    val name: String
)

data class ClassInfoStudentDbRead(
    val number: Int,
    val tid: Int,
    val cid: Int
)

data class StudentDbWrite(
    val number: Int,
    val name: String
)

data class StudentAssociationDbWrite(
    val number: Int,
    val tid: Int,
    val cid: Int
)

data class StudentDbUpdate(
    val number: Int,
    val name: String?,
    val tid: Int?,
    val cid: Int?
)

/**
 * For external use only.
 */

data class CompleteStudentOutputModel(
    val number: Int,
    val name: String,
    val tid: Int
)

data class PersonalInfoStudentOutputModel(
    val number: Int,
    val name: String
)

data class ClassInfoStudentOutputModel(
    val number: Int,
    val tid: Int,
    val cid: Int
)

data class StudentCompactOutputModel(
    val number: Int,
    val name: String,
)

data class StudentInputModel(
    val number: Int,
    val name: String
)

data class StudentAssociationInputModel(
    val number: Int,
    val cid: Int,
    val tid: Int
)

data class StudentUpdateModel(
    val name: String?,
    val tid: Int?,
    val cid: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun StudentInputModel.toDb() = StudentDbWrite(this.number, this.name)
fun StudentAssociationInputModel.toDb(number: Int) = StudentAssociationDbWrite(number, this.tid, this.cid)
fun StudentUpdateModel.toDb(number: Int) = StudentDbUpdate(number, this.name, this.tid, this.cid)
fun CompleteStudentDbRead.toOutput() = CompleteStudentOutputModel(this.number, this.name, this.tid)
fun PersonalInfoStudentDbRead.toOutput() = PersonalInfoStudentOutputModel(this.number, this.name)
fun ClassInfoStudentDbRead.toOutput() = ClassInfoStudentOutputModel(this.number, this.tid, this.cid)
fun CompleteStudentDbRead.toCompactOutput() =StudentCompactOutputModel(this.number,this.name)
