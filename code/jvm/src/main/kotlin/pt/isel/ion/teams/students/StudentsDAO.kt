package pt.isel.ion.teams.students

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import pt.isel.ion.teams.teacher.CompleteTeacherDbRead

interface StudentsDAO {

    @SqlQuery("SELECT * FROM students_view WHERE cid=:classroomId LIMIT :limit OFFSET :offset")
    fun getAllStudentsByClassroom(
        @Bind("classroomId") classroomId: Int,
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int
    ): List<CompleteStudentDbRead>

    @SqlQuery("SELECT * FROM students_view WHERE tid=:tid LIMIT :limit OFFSET :offset")
    fun getAllStudentsByTeam(
        @Bind("tid") tid: Int,
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int
    ): List<CompleteStudentDbRead>

    @SqlQuery("SELECT * FROM students_view WHERE number=:number")
    fun getStudent(@Bind("number") number: Int): CompleteStudentDbRead

    @SqlQuery("SELECT * FROM students_view WHERE githubusername=:username")
    fun getStudentByUsername(@Bind("username") username: String): CompleteStudentDbRead

    /**
     * Action on Student table.
     */
    @SqlUpdate("INSERT INTO student (number, name)  VALUES (:number, :name) ON CONFLICT (number) DO UPDATE SET number = :number, name = :name")
    @GetGeneratedKeys
    fun createStudent(@BindBean student: StudentDbWrite): StudentInfoDbRead

    @SqlUpdate("UPDATE student SET name=COALESCE(:name,name) WHERE number=:number")
    @GetGeneratedKeys
    fun updateStudentName(@BindBean student: StudentDbUpdate): StudentInfoDbRead

    @SqlUpdate("UPDATE student SET githubusername=COALESCE(:githubusername,githubusername) WHERE number=:number")
    @GetGeneratedKeys
    fun updateStudentUsername(@BindBean student: StudentDbUpdate): StudentInfoDbRead

    @SqlUpdate("UPDATE student SET deleted=B'0' WHERE number=:number")
    fun deleteStudent(@Bind number: Int)

    /**
     * Action on Students table.
     */

    @SqlUpdate("INSERT INTO students (number, tid, cid)  VALUES (:number, :tid, :cid) ON CONFLICT (number, tid, cid) DO UPDATE SET deleted=B'0'")
    @GetGeneratedKeys
    fun addStudent(@Bind number: Int, @Bind tid: Int, @Bind cid: Int): StudentClassInfoDbRead

    @SqlUpdate("UPDATE students SET deleted=B'1' WHERE number=:number AND cid=:cid")
    fun removeStudent(@Bind number: Int, @Bind cid: Int)

}