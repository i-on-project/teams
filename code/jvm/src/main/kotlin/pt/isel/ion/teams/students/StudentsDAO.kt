package pt.isel.ion.teams.students

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

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

    @SqlUpdate("INSERT INTO student (number, name)  VALUES (:number, :name) ON CONFLICT (number) DO UPDATE SET number = :number, name = :name")
    @GetGeneratedKeys
    fun createStudent(@BindBean student: StudentDbWrite): PersonalInfoStudentDbRead

    @SqlUpdate("UPDATE students SET tid=COALESCE(:tid,tid), cid=COALESCE(:cid,cid) WHERE number=:number")
    @GetGeneratedKeys
    fun updateStudentTeam(@BindBean student: StudentDbUpdate): ClassInfoStudentDbRead

    @SqlUpdate("UPDATE student SET name=COALESCE(:name,name) WHERE number=:number")
    @GetGeneratedKeys
    fun updateStudentName(@BindBean student: StudentDbUpdate): PersonalInfoStudentDbRead

    @SqlUpdate("UPDATE student SET deleted=B'0' WHERE number=:number")
    fun deleteStudent(@Bind number: Int)

    //For internal use only
    @SqlUpdate("INSERT INTO students (number, tid, cid)  VALUES (:number, :tid, :cid)")
    fun associateStudentToTeam(@BindBean student: StudentAssociationDbWrite)

    //For internal use only
    @SqlUpdate("UPDATE students SET deleted=B'0' WHERE number=:number")
    fun dissociateStudentFromTeam(@Bind number: Int)
}