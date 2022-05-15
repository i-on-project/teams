package pt.isel.ion.teams.teacher


/**
 * For internal use only.
 */
data class CompleteTeacherDbRead(
    val number: Int,
    val name: String,
    val email: String,
    val office: String,
    val cid: Int
)

data class InfoTeacherDbRead(
    val number: Int,
    val name: String,
    val email: String,
    val office: String
)

data class SimpleTeacherDbRead(
    val number: Int,
    val cid: Int
)

data class TeacherDbWrite(
    val number: Int,
    val name: String,
    val email: String,
    val office: String,
    val cid: Int
)

data class TeacherDbUpdate(
    val number: Int,
    val name: String?,
    val email: String?,
    val office: String?,
    val cid: Int?
)

/**
 * For external use only.
 */

data class CompleteTeacherOutputModel(
    val number: Int,
    val name: String,
    val email: String,
    val office: String,
    val cid: Int
)

data class SimpleTeacherOutputModel(
    val number: Int,
    val cid: Int
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
    val cid: Int?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */
fun TeacherInputModel.toDb(cId: Int) = TeacherDbWrite(this.number,this.name,this.email,this.office,cId)
fun TeacherUpdateModel.toDb(number: Int) = TeacherDbUpdate(number,this.name,this.email,this.office, this.cid)
fun CompleteTeacherDbRead.toOutput() = CompleteTeacherOutputModel(this.number,this.name,this.email,this.office,this.cid)
fun SimpleTeacherDbRead.toOutput() = SimpleTeacherOutputModel(this.number, this.cid)
fun InfoTeacherDbRead.toOutput() = TeacherCompactOutputModel(this.number,this.name,this.email,this.office)
fun CompleteTeacherDbRead.toCompactOutput() = TeacherCompactOutputModel(this.number,this.name,this.email,this.office)
