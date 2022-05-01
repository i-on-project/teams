package pt.isel.ion.teams.organizations

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class OrganizationService(val jdbi: Jdbi) {

    fun getAllOrganizations() =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationDAO::class.java).getAllOrganizations()
        }

    fun getOrganization(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationDAO::class.java).getOrganization(id)
        }

    fun createOrganization(organization: OrganizationDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationDAO::class.java).createOrganization(organization)
        }

    fun updateOrganization(organization: OrganizationDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationDAO::class.java).updateOrganization(organization)
        }
}