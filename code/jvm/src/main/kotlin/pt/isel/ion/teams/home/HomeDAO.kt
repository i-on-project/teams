package pt.isel.ion.teams.home

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import pt.isel.ion.teams.classrooms.ClassroomDbRead
import pt.isel.ion.teams.organizations.OrganizationDbRead

interface HomeDAO {

    @SqlQuery("SELECT c.* FROM students s JOIN classrooms_view c ON (s.cid = c.id) WHERE number = :number LIMIT :limit OFFSET :offset")
    fun getStudentClassrooms(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind number: Int
    ): List<ClassroomDbRead>

    @SqlQuery("SELECT o.* FROM teachers t JOIN classrooms c ON (t.cid = c.id) JOIN organizations_view o ON (c.orgid = o.id) WHERE number = :number LIMIT :limit OFFSET :offset")
    fun getTeacherOrganizations(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind number: Int
    ): List<OrganizationDbRead>
}