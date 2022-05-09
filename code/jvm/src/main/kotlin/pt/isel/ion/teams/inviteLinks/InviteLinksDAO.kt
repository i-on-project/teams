package pt.isel.ion.teams.inviteLinks

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface InviteLinksDAO {

    @SqlQuery("SELECT * FROM invite_links_view WHERE cid=:cId")
    fun getAlInviteLinks(@Bind("cId") cId: Int): List<InviteLinksDbRead>

    @SqlQuery("SELECT * FROM invite_links_view WHERE cid=:cId AND code=:code")
    fun getInviteLink(@Bind("cId") cId: Int, @Bind("code") code: String): InviteLinksDbRead

    @SqlUpdate("INSERT INTO invite_links (code,cId) VALUES (:code,:cId)")
    @GetGeneratedKeys
    fun createInviteLink(@Bind("code") code: String, @Bind("cId") cId: Int): InviteLinksDbRead

    @SqlUpdate("UPDATE invite_links SET deleted=B'1' WHERE cid=:cId AND code=:code")
    fun deleteInviteLink(@Bind("cId") cId: Int, @Bind("code") code: String)
}