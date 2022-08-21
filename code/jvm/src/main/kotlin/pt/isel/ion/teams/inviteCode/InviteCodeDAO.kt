package pt.isel.ion.teams.inviteCode

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface InviteCodeDAO {

    @SqlQuery("SELECT * FROM invite_links_view WHERE cid=:cId")
    fun getAlInviteLinks(@Bind("cId") cId: Int): List<InviteLinksDbRead>

    @SqlQuery("SELECT * FROM invite_links_view WHERE code=:code")
    fun getInviteLink( @Bind("code") code: String): InviteLinksDbRead

    @SqlUpdate("INSERT INTO invite_links (code,cId) VALUES (:code,:cId)")
    @GetGeneratedKeys
    fun createInviteLink(@Bind("code") code: String, @Bind("cId") cId: Int): InviteLinksDbRead

    @SqlUpdate("UPDATE invite_links SET deleted=B'1' WHERE code=:code")
    fun deleteInviteLink(@Bind("code") code: String)
}