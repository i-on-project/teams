package pt.isel.ion.teams.teams

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.students.StudentCompactOutputModel

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for a team collection response
 * @param teamsList List of teams to display
 * @param orgId Team's organization id
 * @param classId Team's classroom id
 */
fun CollectionModel.toTeamsSirenObject(
    teamsList: List<TeamsCompactOutputModel>,
    orgId: Int,
    classId: Int
): SirenEntity<CollectionModel> {
    val list = if (teamsList.size > this.pageSize) teamsList.subList(0, this.pageSize) else teamsList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.TEAM),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.TEAM),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(
                    selfLink(Uris.Teams.Team.make(orgId, classId, it.id))
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Teams.make(orgId, classId)),
            if (teamsList.size > pageSize)
                nextLink(Uris.Teams.makePage(pageIndex + 1, pageSize, orgId, classId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Teams.makePage(pageIndex - 1, pageSize, orgId, classId))
            else null,
            SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classId)),
            homeLink(),
            logoutLink()
        )
    )
}

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a team resource response
 * @param studentList List of students in a team
 * @param orgId Team's organization id
 * @param classId Team's classroom id
 */
fun TeamsOutputModel.toStudentSirenObject(
    studentList: List<StudentCompactOutputModel>,
    orgId: Int,
    classId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEAM),
    entities = studentList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.STUDENT),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Students.Student.make(orgId, classId, it.number)))
        )
    },
    links = listOf(
        selfLink(Uris.Teams.make(orgId, classId)),
        homeLink(),
        SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classId)),
        SirenLink(SirenRelations.REPOS, Uris.Repos.make(orgId, classId,this.id)),
        SirenLink(SirenRelations.ASSIGNMENTS, Uris.Assignments.make(orgId, classId)),
        logoutLink()
    )
)

/**
 * Siren definition for a team resource response
 * @param studentList List of students in a team
 * @param orgId Team's organization id
 * @param classId Team's classroom id
 */
fun TeamsOutputModel.toTeacherSirenObject(
    studentList: List<StudentCompactOutputModel>,
    orgId: Int,
    classId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEAM),
    entities = studentList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.STUDENT),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Students.Student.make(orgId, classId, it.number)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-team",
            title = "Update team",
            method = HttpMethod.PUT,
            href = Uris.Teams.Team.make(orgId, classId, id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "state", type = "string"),
            )
        ),
        SirenAction(
            name = "delete-team",
            title = "Delete team",
            method = HttpMethod.DELETE,
            href = Uris.Teams.Team.make(orgId, classId, id),
        )
    ),
    links = listOf(
        selfLink(Uris.Teams.make(orgId, classId)),
        homeLink(),
        SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classId)),
        SirenLink(SirenRelations.REPOS, Uris.Repos.make(orgId, classId,this.id)),
        SirenLink(SirenRelations.NOTES, Uris.Notes.make(orgId, classId, id)),
        logoutLink()
    )
)