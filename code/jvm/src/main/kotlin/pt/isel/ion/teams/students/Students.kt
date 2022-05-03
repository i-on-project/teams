package pt.isel.ion.teams.students

/**
 * For internal use only.
 */

data class StudentDbRead(
    val number: Int,
    val name: String,
    val tId: Int,
    val cId: Int
)

data class StudentDbWrite(
    val number: Int,
    val name: String
)

data class StudentAssociationDbWrite(
    val number: Int,
    val tId: Int,
    val cId: Int
)

data class StudentDbUpdate(
    val number: Int,
    val name: String?,
    val tId: Int?,
    val cId: Int?
)

/**
 * For external use only.
 */

data class StudentOutputModel(
    val number: Int,
    val name: String,
    val tId: Int,
    val cId: Int
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
    val cId: Int,
    val tId: Int
)

data class StudentUpdateModel(
    val name: String?,
    val tId: Int?,
    val cId: Int?
)

/**
 * Functions to transition from external to int\ernal, or vice-versa.
 */

fun StudentInputModel.toDb() = StudentDbWrite(this.number, this.name)
fun StudentAssociationInputModel.toDb(number: Int) = StudentAssociationDbWrite(number, this.tId, this.cId)
fun StudentUpdateModel.toDb(number: Int) = StudentDbUpdate(number, this.name, this.tId, this.cId)
fun StudentDbRead.toOutput() = StudentOutputModel(this.number, this.name, this.tId, this.cId)
fun StudentDbRead.toCompactOutput() =StudentCompactOutputModel(this.number,this.name)
