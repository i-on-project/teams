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
        val name: String,
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

    data class StudentInputModel(
        val number: Int,
        val name: String,
        val tId: Int,
        val cId: Int
    )

    data class StudentUpdateModel(
        val name: String?,
        val tId: Int?,
        val cId: Int?
    )

    /**
     * Functions to transition from external to internal, or vice-versa.
     */

    fun StudentInputModel.toDb() = StudentDbWrite(this.number, this.name, this.tId, this.cId)
    fun StudentUpdateModel.toDb(number: Int) = StudentDbUpdate(number, this.name, this.tId, this.cId)
    fun StudentDbRead.toOutput() = StudentOutputModel(this.number, this.name, this.tId, this.cId)
