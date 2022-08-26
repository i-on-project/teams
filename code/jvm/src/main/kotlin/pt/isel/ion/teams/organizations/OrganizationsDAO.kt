package pt.isel.ion.teams.organizations

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface OrganizationsDAO {

    @SqlQuery("SELECT o.* FROM organizations_view o LIMIT :limit OFFSET :offset")
    fun getAllOrganizationsOfTeacher(@Bind number: Int, @Bind("limit") limit: Int, @Bind("offset") offset: Int): List<OrganizationDbRead>

    @SqlQuery("SELECT * FROM organizations_view WHERE id=:id")
    fun getOrganization(@Bind("id") id: Int): OrganizationDbRead

    @SqlUpdate(
        "INSERT INTO organizations (name, description) " +
                "VALUES (:name,:description) " +
                "ON CONFLICT (name) DO UPDATE SET deleted = B'0', description=:description"
    )
    @GetGeneratedKeys
    fun createOrganization(@BindBean organization: OrganizationDbWrite): OrganizationDbRead

    @SqlUpdate("UPDATE organizations SET name = coalesce(:name, name), description = coalesce(:description, description) WHERE id=:id")
    @GetGeneratedKeys
    fun updateOrganization(@BindBean organization: OrganizationDbUpdate): OrganizationDbRead

    @SqlUpdate("UPDATE organizations SET deleted = B'1' WHERE id = :id")
    fun deleteOrganization(@Bind("id") orgId: Int)
}