package pt.isel.ion.teams.teams

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.students.StudentCompactOutputModel

fun CollectionModel.toTeamsSirenObject(
    teamsList: List<TeamsCompactOutputModel>,
    orgId: Int,
    classroomId: Int
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
                    selfLink(Uris.Teams.Team.make(orgId, classroomId, it.id))
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Teams.make(orgId, classroomId)),
            if (teamsList.size > pageSize)
                nextLink(Uris.Teams.makePage(pageIndex + 1, pageSize, orgId, classroomId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Teams.makePage(pageIndex - 1, pageSize, orgId, classroomId))
            else null,
            SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classroomId)),
            homeLink(),
            logoutLink()
        )
    )
}

fun TeamsOutputModel.toStudentSirenObject(
    studentList: List<StudentCompactOutputModel>,
    orgId: Int,
    classroomId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEAM),
    entities = studentList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.STUDENT),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Students.Student.make(orgId, classroomId, it.number)))
        )
    },
    links = listOf(
        selfLink(Uris.Teams.make(orgId, classroomId)),
        homeLink(),
        SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classroomId)),
        SirenLink(SirenRelations.REPOS, Uris.Repos.make(orgId, classroomId,this.id)),
        SirenLink(SirenRelations.ASSIGNMENTS, Uris.Assignments.make(orgId, classroomId)),
        logoutLink()
    )
)

fun TeamsOutputModel.toTeacherSirenObject(
    studentList: List<StudentCompactOutputModel>,
    orgId: Int,
    classroomId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEAM),
    entities = studentList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.STUDENT),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Students.Student.make(orgId, classroomId, it.number)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-team",
            title = "Update team",
            method = HttpMethod.PUT,
            href = Uris.Teams.Team.make(orgId, classroomId, id),
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
            href = Uris.Teams.Team.make(orgId, classroomId, id),
        ),
        SirenAction(
            name = "create-note",
            title = "Create Note",
            method = HttpMethod.POST,
            href = Uris.Notes.make(orgId, classroomId, id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "description", type = "string"),
            )
        ),

    ),
    links = listOf(
        selfLink(Uris.Teams.make(orgId, classroomId)),
        homeLink(),
        SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classroomId)),
        SirenLink(SirenRelations.REPOS, Uris.Repos.make(orgId, classroomId,this.id)),
        SirenLink(SirenRelations.NOTES, Uris.Notes.make(orgId, classroomId, id)),
        logoutLink()
    )
)