package pt.isel.ion.teams.inviteLinks

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface InviteLinksDAO {

    @SqlQuery("SELECT * FROM invite_links_view WHERE cid=:cid")
    fun getInviteLink(@Bind("cId") cId: Int): InviteLinksDbRead

    @SqlQuery("INSERT INTO invite_links (code,cId) VALUES (:code,:cId)")
    @GetGeneratedKeys
    fun createInviteLink(@Bind("code") code: String, @Bind("cId") cId: Int): InviteLinksDbRead

    @SqlQuery("UPDATE invite_links SET deleted=B'1' WHERE cid=:cId")
    fun deleteInviteLink( @Bind("cId") cId: Int)
}