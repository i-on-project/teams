package pt.isel.ion.teams.organizations

import org.jdbi.v3.sqlobject.statement.SqlQuery

interface OrganizationDAO {
    @SqlQuery("SELECT * FROM organization")
    fun getAllOrganizations(): List<Organization>
}