package pt.isel.ion.teams.inviteCode

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Data Access Invite-code for the Assignment resource. This is an interface responsible for defining the methods for
 * accessing data of the given resource.
 */
interface InviteCodeDAO {

    @SqlQuery("SELECT * FROM invite_codes_view WHERE cid=:cId")
    fun getAlInviteLinks(@Bind("cId") cId: Int): List<InviteCodesDbRead>

    @SqlQuery("SELECT * FROM invite_codes_view WHERE code=:code")
    fun getInviteLink( @Bind("code") code: String): InviteCodesDbRead

    @SqlUpdate("INSERT INTO invite_codes (code,cId) VALUES (:code,:cId)")
    @GetGeneratedKeys
    fun createInviteLink(@Bind("code") code: String, @Bind("cId") cId: Int): InviteCodesDbRead

    @SqlUpdate("UPDATE invite_codes SET deleted=B'1' WHERE code=:code")
    fun deleteInviteLink(@Bind("code") code: String)
}