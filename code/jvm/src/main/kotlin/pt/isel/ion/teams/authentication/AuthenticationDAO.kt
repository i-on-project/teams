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
    @SqlUpdate("INSERT INTO to_verify (code, number) VALUES (:code, :number)")
    fun createVerification(@Bind code: String, @Bind number: Int)

    @SqlUpdate("DELETE FROM to_verify WHERE code=:code")
    fun verifyUser(@Bind code: String)

    @SqlQuery("SELECT * FROM to_verify WHERE number=:number")
    fun isVerified(@Bind number: Int): Boolean

}