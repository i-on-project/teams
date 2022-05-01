package pt.isel.ion.teams.organizations

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface OrganizationDAO {

    @SqlQuery("SELECT * FROM organizations_view LIMIT :limit OFFSET :offset")
    fun getAllOrganizations(@Bind("limit") limit: Int, @Bind("offset") offset: Int): List<OrganizationDbRead>

    @SqlQuery("SELECT * FROM organizations_view WHERE id=:id")
    fun getOrganization(@Bind("id") id: Int): OrganizationDbRead

    @SqlUpdate("INSERT INTO organizations_view (name, description) VALUES (:name,:description)")
    @GetGeneratedKeys
    fun createOrganization(@BindBean organization: OrganizationDbWrite): OrganizationDbRead

    @SqlUpdate("UPDATE organizations_view SET name=:name, description=:description WHERE id=:id")
    @GetGeneratedKeys
    fun updateOrganization(@BindBean organization: OrganizationDbUpdate): Int

}