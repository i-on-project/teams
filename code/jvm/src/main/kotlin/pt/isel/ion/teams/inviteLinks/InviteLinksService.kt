package pt.isel.ion.teams.inviteLinks

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler
import java.util.UUID

@Component
class InviteLinksService(val jdbi: Jdbi) {

    fun getInviteLink(cId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(InviteLinksDAO::class.java).getInviteLink(cId)
        }

    fun createInviteLink(cId: Int) =
        sqlExceptionHandler {
            val code = UUID.randomUUID().toString()
            jdbi.onDemand(InviteLinksDAO::class.java).createInviteLink(code, cId)
        }

    fun deleteInviteLink(cId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(InviteLinksDAO::class.java).deleteInviteLink(cId)
        }
}