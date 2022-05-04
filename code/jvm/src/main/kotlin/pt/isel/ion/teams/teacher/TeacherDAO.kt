package pt.isel.ion.teams.teacher

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TeacherDAO {

    @SqlQuery("SELECT * FROM teachers_view WHERE cid=:classroomId LIMIT :limit OFFSET :offset")
    fun getTeachers(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("classroomId") classroomId: Int
    ): List<TeacherDbRead>

    @SqlQuery("SELECT * FROM teachers_view WHERE number=:number")
    fun getTeacher(@Bind("number") number: Int): TeacherDbRead

    @SqlUpdate("INSERT INTO teacher (number,name,email,office) VALUES (:number,:name,:email,:office)")
    @GetGeneratedKeys
    fun createTeacher(@BindBean teacher: TeacherDbWrite): TeacherDbRead

    @SqlUpdate("UPDATE teacher SET name=COALESCE(:name,name),email=COALESCE(:email,email),office=COALESCE(:office,office) WHERE number=:number")
    @GetGeneratedKeys
    fun updateTeacherInfo(@BindBean teacher: TeacherDbUpdate): TeacherDbRead

    @SqlUpdate("UPDATE teachers SET cid=COALESCE(:cid,cid) WHERE number=:number")
    @GetGeneratedKeys
    fun updateTeacherClass(@BindBean teacher: TeacherDbUpdate): TeacherDbRead
}