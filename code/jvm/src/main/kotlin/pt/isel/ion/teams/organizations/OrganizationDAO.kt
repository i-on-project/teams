package pt.isel.ion.teams.organizations

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface OrganizationDAO {

    @SqlQuery("SELECT * FROM organizations")
    fun getAllOrganizations(): List<OrganizationDbRead>

    @SqlQuery("SELECT * FROM organizations WHERE id=:id")
    fun getOrganization(@Bind("id") id: Int): OrganizationDbRead

    @SqlUpdate("INSERT INTO organizations (name, description) VALUES (:name,:description)")
    @GetGeneratedKeys
    fun createOrganization(@BindBean organization: OrganizationDbWrite): OrganizationDbRead

    @SqlUpdate("UPDATE organizations SET name=:name, description=:description WHERE id=:id")
    @GetGeneratedKeys
    fun updateOrganization(@BindBean organization: OrganizationDbUpdate): Int

}