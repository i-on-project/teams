package pt.isel.ion.teams.teacher

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TeachersDAO {

    @SqlQuery("SELECT * FROM teachers_view WHERE cid=:classId LIMIT :limit OFFSET :offset")
    fun getTeachers(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("classId") classId: Int
    ): List<CompleteTeacherDbRead>

    @SqlQuery("SELECT * FROM teachers_view WHERE number=:number")
    fun getTeacher(@Bind("number") number: Int): CompleteTeacherDbRead

    @SqlUpdate("INSERT INTO teacher (number,name,email,office) VALUES (:number,:name,:email,:office)")
    @GetGeneratedKeys
    fun createTeacher(@BindBean teacher: TeacherDbWrite): InfoTeacherDbRead

    @SqlUpdate("UPDATE teacher SET name=COALESCE(:name,name),email=COALESCE(:email,email),office=COALESCE(:office,office) WHERE number=:number")
    @GetGeneratedKeys
    fun updateTeacherInfo(@BindBean teacher: TeacherDbUpdate): InfoTeacherDbRead

    @SqlUpdate("UPDATE teachers SET cid=COALESCE(:cid,cid) WHERE number=:number")
    @GetGeneratedKeys
    fun updateTeacherClass(@BindBean teacher: TeacherDbUpdate): SimpleTeacherDbRead
}