package pt.isel.ion.teams.teams

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.repos.RepoCompactOutputModel

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
            if (teamsList.size > pageSize) nextLink(Uris.Teams.makePage(pageIndex + 1, pageSize))
            else null,
            if (pageIndex > 0) prevLink(Uris.Teams.makePage(pageIndex - 1, pageSize)) else null,
            SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classroomId)),
            homeLink(),
            logoutLink()
        )
    )
}

fun TeamsOutputModel.toStudentSirenObject(
    repoList: List<RepoCompactOutputModel>,
    orgId: Int,
    classroomId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEAM),
    entities = repoList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.REPO),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Repos.Repo.make(orgId, classroomId, this.id, it.id)))
        )
    },
    links = listOf(
        selfLink(Uris.Teams.make(orgId, classroomId)),
        homeLink(),
        SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classroomId)),
        SirenLink(SirenRelations.ASSIGNMENTS, Uris.Assignments.make(orgId, classroomId)),
        logoutLink()
    )
)

fun TeamsOutputModel.toSTeacherSirenObject(
    repoList: List<RepoCompactOutputModel>,
    orgId: Int,
    classroomId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEAM),
    entities = repoList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.REPO),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Repos.Repo.make(orgId, classroomId, this.id, it.id)))
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
        SirenLink(SirenRelations.NOTES, Uris.Notes.make(orgId, classroomId, id)),
        logoutLink()
    )
)