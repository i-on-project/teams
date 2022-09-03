package pt.isel.ion.teams.organizations

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

/**
 * Service for the Organization resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
@Component
class OrganizationsService(val jdbi: Jdbi) {

    fun getAllOrganizationsOfTeacher(number: Int, pageSize: Int, pageIndex: Int) =
        sqlExceptionHandler {
            jdbi
                .onDemand(OrganizationsDAO::class.java)
                .getAllOrganizationsOfTeacher(number, pageSize + 1, pageIndex * pageSize)
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