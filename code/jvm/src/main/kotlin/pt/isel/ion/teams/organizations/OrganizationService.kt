package pt.isel.ion.teams.organizations

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class OrganizationService(val jdbi: Jdbi) {

    fun getAllOrganizations() = jdbi.onDemand(OrganizationDAO::class.java).getAllOrganizations()

    fun getOrganization(id: Int) = jdbi.onDemand(OrganizationDAO::class.java).getOrganization(id)

    fun createOrganization(organization: OrganizationDbWrite) =
        jdbi.onDemand(OrganizationDAO::class.java).createOrganization(organization)


    fun updateOrganization(organization: OrganizationDbUpdate) =
        jdbi.onDemand(OrganizationDAO::class.java).updateOrganization(organization)


}