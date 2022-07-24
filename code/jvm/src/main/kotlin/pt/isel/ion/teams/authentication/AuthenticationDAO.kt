package pt.isel.ion.teams.authentication

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AuthenticationDAO {

    /* User Session */
    @SqlUpdate("INSERT INTO user_session (number, sessionid, usertype) VALUES (:number, :sessionId, :usertype)")
    fun createSession(@Bind number: Int, @Bind sessionId: String, @Bind usertype: Char)

    @SqlUpdate("DELETE FROM user_session WHERE number=:number AND sessionid=:sessionId")
    fun deleteSession(@Bind number: Int, @Bind sessionId: String)

    /* Verification of users (Teacher or Student) */
    @SqlUpdate("UPDATE teacher SET verified=B'1' WHERE number=:number")
    fun verifyTeacher(@Bind number: Int)

    @SqlUpdate("UPDATE student SET verified=B'1' WHERE number=:number")
    fun verifyStudent(@Bind number: Int)

    @SqlQuery("SELECT verified FROM teacher WHERE number=:number")
    fun isVerifiedTeacher(@Bind number: Int): Boolean

    @SqlQuery("SELECT verified FROM student WHERE number=:number")
    fun isVerifiedStudent(@Bind number: Int): Boolean

}