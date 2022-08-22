package pt.isel.ion.teams.classrooms

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.teams.TeamsCompactOutputModel
import java.net.URI

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for a classroom collection response
 * @param classroomList List of classrooms meant to send
 * @param orgId Classroom's organization id
 */
fun CollectionModel.toClassroomSirenObject(
    classroomList: List<ClassroomOutputModel>,
    orgId: Int
): SirenEntity<CollectionModel> {
    val list = if (classroomList.size > this.pageSize) classroomList.subList(0, this.pageSize) else classroomList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.CLASSROOM),
        entities = list.map {
            EmbeddedEntity(
                properties = it.toCompactOutput(),
                clazz = listOf(SirenClasses.CLASSROOM),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(
                    selfLink(Uris.Classrooms.Classroom.make(orgId, it.id))
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Classrooms.makePage(pageIndex,pageSize,orgId)),
            if (classroomList.size > pageSize)
                nextLink(Uris.Classrooms.makePage(pageIndex + 1, pageSize, orgId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Classrooms.makePage(pageIndex - 1, pageSize, orgId))
            else null,
            SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
            homeLink(),
            logoutLink(),
        )
    )
}

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a student's classroom resource response
 * @param teamsList List of teams of a student in a given classroom
 * @param orgId Classroom's organization id
 * @param teamId Student's classroom team id
 */
fun ClassroomOutputModel.toStudentSirenObject(
    teamsList: List<TeamsCompactOutputModel>,
    orgId: Int,
    teamId: Int
) = SirenEntity(
    properties = this.toCompactOutput(),
    clazz = listOf(SirenClasses.CLASSROOM),
    entities = teamsList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.TEAM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Teams.Team.make(orgId, this.id, it.id)))
        )
    },
    links = listOf(
        selfLink(Uris.Classrooms.make(orgId)),
        homeLink(),
        SirenLink(SirenRelations.GITHUB, URI(this.githubURI)),
        SirenLink(SirenRelations.AVATAR, URI(this.avatarURI)),
        SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
        SirenLink(SirenRelations.ASSIGNMENTS, Uris.Assignments.make(orgId, id)),
        SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, id, teamId)),
        logoutLink()
    )
)

/**
 * Siren definition for a teacher's classroom resource response
 * @param teamsList List of teams of a given classroom
 * @param orgId Classroom's organization id
 */
fun ClassroomOutputModel.toTeacherSirenObject(
    teamsList: List<TeamsCompactOutputModel>,
    orgId: Int,
) = SirenEntity(
    properties = this.toCompactOutput(),
    clazz = listOf(SirenClasses.CLASSROOM),
    entities = teamsList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.TEAM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Teams.Team.make(orgId, this.id, it.id)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "create-assignment",
            title = "Create Assignment",
            method = HttpMethod.POST,
            href = Uris.Assignments.make(orgId, id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "releaseDate", type = "string"),
                SirenAction.Field(name = "description", type = "string"),
            )
        ),
        SirenAction(
            name = "delete-classroom",
            title = "Delete Classroom",
            method = HttpMethod.DELETE,
            href = Uris.Classrooms.Classroom.make(orgId, id)
        ),
        SirenAction(
            name = "update-classroom",
            title = "Update Classroom",
            method = HttpMethod.PUT,
            href = Uris.Classrooms.Classroom.make(orgId, id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "state", type = "string"),
                SirenAction.Field(name = "description", type = "string"),
                SirenAction.Field(name = "schoolYear", type = "string"),
                SirenAction.Field(name = "maxGroups", type = "number"),
                SirenAction.Field(name = "maxGroupMembers", type = "number"),
            )
        ),
        SirenAction(
            name = "create-invite-link",
            title = "Create Invite-Link",
            method = HttpMethod.POST,
            href = Uris.InviteCodes.make(orgId, id),
        ),
    ),
    links = listOf(
        selfLink(Uris.Classrooms.make(orgId)),
        homeLink(),
        SirenLink(SirenRelations.GITHUB, URI(this.githubURI)),
        SirenLink(SirenRelations.AVATAR, URI(this.avatarURI)),
        SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
        SirenLink(SirenRelations.ASSIGNMENTS, Uris.Assignments.make(orgId, id)),
        SirenLink(SirenRelations.REQUESTS, Uris.Requests.make(orgId, id)),
        SirenLink(SirenRelations.INVITE_CODES, Uris.InviteCodes.make(orgId, id)),
        SirenLink(SirenRelations.STUDENTS, Uris.Students.FromClassroom.make(orgId, id)),
        logoutLink()
    )
)