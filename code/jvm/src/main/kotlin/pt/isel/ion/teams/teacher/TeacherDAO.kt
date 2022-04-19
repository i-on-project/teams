package pt.isel.ion.teams.teacher

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TeacherDAO {

    @SqlQuery("SELECT * FROM teacher WHERE cId=:classroomId")
    fun getTeachers(@Bind("classroomId") classroomId: Int): List<TeacherDbRead>

    @SqlQuery("SELECT * FROM teacher WHERE number=:number")
    fun getTeacher(@Bind("number")number: Int): TeacherDbRead

    @SqlUpdate("INSERT INTO teacher (number,name,email,office) VALUES (:number,:name,:email,:office)")
    @GetGeneratedKeys
    fun createTeacher(@BindBean teacher: TeacherDbWrite): TeacherDbRead

    @SqlUpdate("UPDATE teacher SET name=:name,email=:email,office=:office WHERE number=:number")
    @GetGeneratedKeys
    fun updateTeacher(@BindBean teacher: TeacherDbUpdate): Int
}