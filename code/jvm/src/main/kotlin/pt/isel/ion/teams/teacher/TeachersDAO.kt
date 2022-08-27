package pt.isel.ion.teams.teacher

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TeachersDAO {

    @SqlQuery("SELECT * FROM teacher_classrooms_view WHERE id=:classId LIMIT :limit OFFSET :offset")
    fun getTeachersByClass(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("classId") classId: Int
    ): List<CompleteTeacherDbRead>

    @SqlQuery("SELECT * FROM teachers_view WHERE number=:number")
    fun getTeacher(@Bind("number") number: Int): CompleteTeacherDbRead

    @SqlQuery("SELECT * FROM teacher WHERE githubusername=:username AND deleted = B'0'")
    fun getTeacherByUsername(@Bind("username") username: String): InfoTeacherDbRead

    /**
     * Actions on table teachers.
     */
    @SqlUpdate("INSERT INTO teacher (number,name,email,office) VALUES (:number,:name,:email,:office) ON CONFLICT (number) DO UPDATE set name = :name, email = :email, office = :office, deleted = B'0'")
    @GetGeneratedKeys
    fun createTeacher(@BindBean teacher: TeacherDbWrite): InfoTeacherDbRead

    @SqlUpdate("UPDATE teacher SET name=COALESCE(:name,name), email=COALESCE(:email,email), office=COALESCE(:office,office), githubusername=COALESCE(:githubusername,githubusername) WHERE number=:number")
    @GetGeneratedKeys
    fun updateTeacherInfo(@BindBean teacher: TeacherDbUpdate): InfoTeacherDbRead

    @SqlUpdate("UPDATE teacher SET deleted = B'1' WHERE number = :number")
    fun deleteTeacher(@Bind("number") number: Int)

    /**
     * Actions on table teachers.
     */
    @SqlUpdate("INSERT INTO teachers_classroom (number, cid, orgid) VALUES (:number,:cid, :orgid)")
    @GetGeneratedKeys
    fun addTeacherToClassroom(@BindBean simpleTeacherDbRead: SimpleTeacherDbRead): SimpleTeacherDbRead

    @SqlUpdate("INSERT INTO teachers_organization (number, orgid) VALUES (:number,:orgid) ON CONFLICT (number,orgid) do nothing")
    @GetGeneratedKeys
    fun addTeacherToOrganization(@BindBean simpleTeacherDbRead: SimpleTeacherDbRead): SimpleTeacherDbRead

    @SqlUpdate("DELETE FROM teachers_classroom WHERE number=:number AND cid=:cid AND orgid=:orgid")
    fun removeTeacherFromClassroom(@BindBean simpleTeacherDbRead: SimpleTeacherDbRead)

    @SqlUpdate("DELETE FROM teachers_organization WHERE number=:number AND orgid=:orgid")
    fun removeTeacherFromOrganization(@BindBean simpleTeacherDbRead: SimpleTeacherDbRead)
}