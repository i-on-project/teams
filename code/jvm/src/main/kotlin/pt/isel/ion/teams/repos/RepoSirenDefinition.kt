package pt.isel.ion.teams.repos

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.tags.TagCompactOutputModel
import java.net.URI

fun CollectionModel.toRepoSirenObject(
    repoList: List<RepoCompactOutputModel>,
    orgId: Int,
    classId: Int,
    teamId: Int
): SirenEntity<CollectionModel> {
    val list = if (repoList.size > this.pageSize) repoList.subList(0, this.pageSize) else repoList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.REPO),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.REPO),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Repos.Repo.make(orgId, classId, teamId, it.id)))
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Repos.make(orgId, classId, teamId)),
            if (repoList.size > pageSize)
                nextLink(Uris.Repos.makePage(pageIndex + 1, pageSize, orgId, classId, teamId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Repos.makePage(pageIndex - 1, pageSize, orgId, classId, teamId))
            else null,
            homeLink(),
            SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, classId, teamId)),
            logoutLink()
        )
    )
}

fun RepoOutputModel.toStudentSirenObject(
    orgId: Int,
    classId: Int,
    teamId: Int,
    assId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.REPO),
    links = listOf(
        selfLink(Uris.Repos.Repo.make(orgId, classId, teamId, id)),
        homeLink(),
        SirenLink(SirenRelations.GITHUB, URI(url)),
        SirenLink(SirenRelations.REPOS, Uris.Repos.make(orgId, classId, teamId)),
        SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, classId, teamId)),
        SirenLink(SirenRelations.ASSIGNMENT, Uris.Assignments.Assignment.make(orgId, classId, assId)),
        logoutLink()
    )
)

fun RepoOutputModel.toTeacherSirenObject(
    tagsList: List<TagCompactOutputModel>,
    orgId: Int,
    classId: Int,
    teamId: Int,
    assId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.REPO),
    entities = tagsList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.TAG),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Tags.Tag.make(orgId, classId, teamId, this.id, it.id)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-repo",
            title = "Update Repo",
            method = HttpMethod.PUT,
            href = Uris.Repos.Repo.make(orgId, classId, teamId, id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "url", type = "string"),
                SirenAction.Field(name = "assId", type = "number"),
            )
        ),
        SirenAction(
            name = "delete-repo",
            title = "Delete Repo",
            method = HttpMethod.DELETE,
            href = Uris.Repos.Repo.make(orgId, classId, teamId, id),
        )
    ),
    links = listOf(
        selfLink(Uris.Repos.Repo.make(orgId, classId, teamId, id)),
        homeLink(),
        SirenLink(SirenRelations.GITHUB, URI(url)),
        SirenLink(SirenRelations.TAGS, Uris.Tags.make(orgId, classId, teamId, id)),
        SirenLink(SirenRelations.REPOS, Uris.Repos.make(orgId, classId, teamId)),
        SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, classId, teamId)),
        SirenLink(SirenRelations.ASSIGNMENT, Uris.Assignments.Assignment.make(orgId, classId, assId)),
        logoutLink()
    )
)