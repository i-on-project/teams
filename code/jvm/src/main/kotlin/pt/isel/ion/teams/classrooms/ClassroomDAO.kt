package pt.isel.ion.teams.classrooms

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.time.ZoneId

interface ClassroomDAO {

    @SqlQuery("SELECT * FROM classrooms WHERE orgid =: orgId LIMIT :limit OFFSET :offset")
    fun getAllClassroomsByOrganizationWithPaging(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("orgId") orgId: Int
    ): List<ClassroomDbRead>

    @SqlQuery("SELECT * FROM classrooms WHERE orgid =: orgId")
    fun getAllClassroomsByOrganization(
        @Bind("orgId") orgId: Int
    ): List<ClassroomDbRead>

    @SqlQuery("SELECT * FROM classrooms WHERE id =: id")
    fun getClassroom(@Bind("id") id: Int): ClassroomDbRead

    @SqlUpdate("INSERT INTO classrooms (name, description, maxteams, maxmembersperteam, linkrepo, schoolyear, orgid, state) VALUES (:name, :description, :maxteams, :maxmembersperteam, :linkrepo, :schoolyear, :orgid, :state)")
    @GetGeneratedKeys
    fun createClassroom(@BindBean classroomDbWrite: ClassroomDbWrite): ClassroomDbRead

    @SqlUpdate("UPDATE classrooms SET name =: name, description =: description, maxteams =: maxgroups, maxmembersperteam =: maxmemberspergroup, state =: state, schoolyear =: schoolyear WHERE id =: id")
    @GetGeneratedKeys
    fun updateClassroom(@BindBean classroomUpdateModel: ClassroomDbUpdate): ClassroomDbRead

    @SqlUpdate("UPDATE classrooms SET deleted = B'1' WHERE id =: id")
    fun deleteClassroom(@Bind("id") classroomId: Int)
}