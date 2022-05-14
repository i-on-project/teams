package pt.isel.ion.teams.organizations

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

@Component
class OrganizationsService(val jdbi: Jdbi) {

    fun getAllOrganizations(pageSize: Int, pageIndex: Int) =
        sqlExceptionHandler {
            jdbi
                .onDemand(OrganizationsDAO::class.java)
                .getAllOrganizations(pageSize + 1, pageIndex * pageSize)
        }

    fun getOrganization(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationsDAO::class.java).getOrganization(id)
        }

    fun createOrganization(organization: OrganizationDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationsDAO::class.java).createOrganization(organization)
        }

    fun updateOrganization(organization: OrganizationDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationsDAO::class.java).updateOrganization(organization)
        }

    fun deleteOrganization(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(OrganizationsDAO::class.java).deleteOrganization(id)
        }
}