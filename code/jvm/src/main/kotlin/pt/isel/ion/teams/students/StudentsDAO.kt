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
    @GetGeneratedKeys
    fun associateStudent(@BindBean student: StudentDbWrite)

    @SqlUpdate("INSERT INTO students (number, name, tid, cid)  VALUES (:number, :name, :tid, :cid)")
    @GetGeneratedKeys
    fun createStudent(@BindBean student: StudentDbWrite): StudentDbRead

    @SqlUpdate("UPDATE students SET name=:name, tid=:tid, cid=:cid WHERE number=:number")
    @GetGeneratedKeys
    fun updateStudent(@BindBean student: StudentDbUpdate): Int

}