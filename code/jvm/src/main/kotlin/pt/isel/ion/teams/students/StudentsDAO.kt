package pt.isel.ion.teams.students

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface StudentsDAO {

    @SqlQuery("SELECT * FROM students_view WHERE cid=:classroomId")
    fun getAllStudentsByClassroom(@Bind("classroomId") classroomId: Int): List<StudentDbRead>

    @SqlQuery("SELECT * FROM students_view WHERE tid=:teamsId")
    fun getAllStudentsByTeam(@Bind("teamsId") teamsId: Int): List<StudentDbRead>

    @SqlUpdate("INSERT INTO students (number, tid, cid)  VALUES (:number, :tid, :cid)")

    fun associateStudentToTeam(@BindBean student: StudentAssociationDbWrite)

    @SqlUpdate("INSERT INTO student (number, name)  VALUES (:number, :name)")
    fun createStudent(@BindBean student: StudentDbWrite)

    @SqlUpdate("UPDATE students SET tid=COALESCE(:tid,tid), cid=COALESCE(:cid,cid) WHERE number=:number")
    fun updateStudentTeam(@BindBean student: StudentDbUpdate)

    @SqlUpdate("UPDATE student SET name=COALESCE(:name,name) WHERE number=:number")
    fun updateStudentName(@BindBean student: StudentDbUpdate)
}