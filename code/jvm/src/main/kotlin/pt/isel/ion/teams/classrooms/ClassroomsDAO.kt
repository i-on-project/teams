package pt.isel.ion.teams.classrooms

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Data Access Object for the Classroom resource. This is an interface responsible for defining the methods for
 * accessing data of the given resource.
 */
interface ClassroomsDAO {

    @SqlQuery("SELECT id, name, description, maxTeams, maxmembersperteam, schoolYear, orgId, state " +
            "FROM teacher_classrooms_view AS c WHERE number=:number AND orgId=:orgId LIMIT :limit OFFSET :offset")
    fun getAllClassroomsByOrganizationOfTeacherWithPaging(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("number") number: Int,
        @Bind("orgId") orgId: Int
    ): List<ClassroomDbRead>

    @SqlQuery("SELECT * FROM classrooms_view WHERE orgid = :orgId ORDER BY orgid LIMIT :limit OFFSET :offset")
    fun getAllClassroomsByOrganization(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("orgId") orgId: Int
    ): List<ClassroomDbRead>

    @SqlQuery("SELECT id, name, description, maxTeams, maxMembersPerTeam, schoolYear, orgId, state FROM teacher_classrooms_view AS cl WHERE cl.number = :number")
    fun getAllClassroomsByTeacher(@Bind("number") teacherNum: Int): List<ClassroomDbRead>

    @SqlQuery("SELECT * FROM classrooms_view WHERE id = :id")
    fun getClassroom(@Bind("id") id: Int): ClassroomDbRead

    @SqlUpdate(
        "INSERT INTO classrooms (name, description, maxteams, maxmembersperteam, schoolyear, orgid) " +
                "VALUES (:name, :description, :maxTeams, :maxMembersPerTeam, :schoolYear, :orgId) " +
                "ON CONFLICT (name,orgid) DO UPDATE SET deleted = B'0', name=:name, " +
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