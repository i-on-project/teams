package pt.isel.ion.teams.organizations

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class OrganizationService(val jdbi: Jdbi) {

    fun getAllOrganizations() = jdbi.onDemand(OrganizationDAO::class.java).getAllOrganizations().map { it.toOutput() }

    fun getOrganization(id: Int) = jdbi.onDemand(OrganizationDAO::class.java).getOrganization(id).toOutput()

    fun createOrganization(organization: OrganizationInput) =
        jdbi.onDemand(OrganizationDAO::class.java).createOrganization(organization.toDb())

    fun updateOrganization(organization: OrganizationUpdate) =
        jdbi.onDemand(OrganizationDAO::class.java).updateOrganization(organization.toDb())


}