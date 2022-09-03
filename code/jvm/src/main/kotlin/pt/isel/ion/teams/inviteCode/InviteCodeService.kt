package pt.isel.ion.teams.inviteCode

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler
import java.util.UUID

/**
 * Service for the Invite-code resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
@Component
class InviteCodeService(val jdbi: Jdbi) {

    fun getAllInviteLinks(pageSize: Int, pageIndex: Int, cId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(InviteCodeDAO::class.java).getAlInviteLinks(cId)
        }

    fun getInviteLink(code: String) =
        sqlExceptionHandler {
            jdbi.onDemand(InviteCodeDAO::class.java).getInviteLink(code)
        }

    fun createInviteLink(cId: Int) =
        sqlExceptionHandler {
            val code = UUID.randomUUID().toString()
            jdbi.onDemand(InviteCodeDAO::class.java).createInviteLink(code, cId)
        }

    fun deleteInviteLink(code: String) =
        sqlExceptionHandler {
            jdbi.onDemand(InviteCodeDAO::class.java).deleteInviteLink(code)
        }
}