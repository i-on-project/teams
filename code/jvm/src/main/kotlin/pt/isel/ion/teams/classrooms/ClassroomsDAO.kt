package pt.isel.ion.teams.classrooms

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface ClassroomsDAO {

    @SqlQuery("SELECT * FROM classrooms_view WHERE orgid = :orgId ORDER BY orgid LIMIT :limit OFFSET :offset")
    fun getAllClassroomsByOrganizationWithPaging(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("orgId") orgId: Int
    ): List<ClassroomDbRead>

    @SqlQuery("SELECT * FROM classrooms_view WHERE orgid = :orgId ORDER BY orgid")
    fun getAllClassroomsByOrganization(
        @Bind("orgId") orgId: Int
    ): List<ClassroomDbRead>

    @SqlQuery("SELECT * FROM classrooms_view WHERE id = :id")
    fun getClassroom(@Bind("id") id: Int): ClassroomDbRead

    @SqlUpdate(
        "INSERT INTO classrooms (name, description, maxteams, maxmembersperteam, repouri, schoolyear, orgid,githuburi,avataruri) " +
                "VALUES (:name, :description, :maxTeams, :maxMembersPerTeam, :repoURI, :schoolYear, :orgId,:githubURI,:avatarURI) " +
                "ON CONFLICT (repouri,githuburi,avataruri) DO UPDATE SET deleted = B'0', name=:name, " +
                "description=:description, maxteams=:maxTeams, maxmembersperteam=:maxMembersPerTeam, " +
                "schoolyear=:schoolYear, orgid=:orgId"
    )
    @GetGeneratedKeys
    fun createClassroom(@BindBean classroomDbWrite: ClassroomDbWrite): ClassroomDbRead

    @SqlUpdate("UPDATE classrooms SET name = coalesce(:name, name), description = coalesce(:description, description), " +
            "maxteams = coalesce(:maxTeams, maxteams), maxmembersperteam = coalesce(:maxMembersPerTeam, maxmembersperteam), " +
            "state = coalesce(:state, state), schoolyear = coalesce(:schoolYear, schoolyear) WHERE id = :id")
    @GetGeneratedKeys
    fun updateClassroom(@BindBean classroomUpdateModel: ClassroomDbUpdate): ClassroomDbRead

    @SqlUpdate("UPDATE classrooms SET deleted = B'1' WHERE id = :id")
    fun deleteClassroom(@Bind("id") classroomId: Int)
}