package pt.isel.ion.teams.organizations

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.classrooms.ClassroomCompactOutputModel
import pt.isel.ion.teams.common.Uris
import java.net.URI

fun CollectionModel.toOrganizationsSirenObject(orgsList: List<OrganizationOutputModel>): SirenEntity<CollectionModel> {
    val list = if (orgsList.size > this.pageSize) orgsList.subList(0, this.pageSize) else orgsList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.ORGANIZATION),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                rel = listOf(SirenRelations.ITEM),
                clazz = listOf(SirenClasses.ORGANIZATION),
                links = listOf(
                    selfLink(Uris.Organizations.Organization.make(it.id))
                )
            )
        },
        links = listOfNotNull(
            selfLink(URI(Uris.Organizations.MAIN_PATH)),
            if (orgsList.size > pageSize) nextLink(Uris.Organizations.makePage(pageIndex + 1, pageSize))
            else null,
            if (pageIndex > 0) prevLink(Uris.Organizations.makePage(pageIndex - 1, pageSize)) else null,
            homeLink(),
            logoutLink()
        )
    )
}

fun OrganizationOutputModel.toStudentSirenObject(classroomList: List<ClassroomCompactOutputModel>) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.ORGANIZATION),
    entities = classroomList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.CLASSROOM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Classrooms.Classroom.make(this.id, it.id)))
        )
    },
    links = listOf(
        selfLink(Uris.Organizations.Organization.make(id)),
        homeLink(),
        SirenLink(SirenRelations.GITHUB, URI(githubUri)),
        logoutLink(),
        SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(id)),
        SirenLink(SirenRelations.ORGANIZATIONS, URI(Uris.Organizations.MAIN_PATH))
    )
)

fun OrganizationOutputModel.toTeacherSirenObject(classroomList: List<ClassroomCompactOutputModel>) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.ORGANIZATION),
    entities = classroomList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.CLASSROOM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Classrooms.Classroom.make(this.id, it.id)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "create-classroom",
            title = "Create Classroom",
            method = HttpMethod.POST,
            href = Uris.Classrooms.make(id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "description", type = "string"),
                SirenAction.Field(name = "schoolYear", type = "string"),
                SirenAction.Field(name = "maxGroups", type = "string"),
                SirenAction.Field(name = "maxMembersPerGroup", type = "string"),
            )
        ),
        SirenAction(
            name = "update-organization",
            title = "Update Organization",
            method = HttpMethod.PUT,
            href = Uris.Organizations.Organization.make(id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "state", type = "string"),
                SirenAction.Field(name = "description", type = "string"),
            )
        ),
    ),
    links = listOf(
        selfLink(Uris.Organizations.Organization.make(id)),
        homeLink(),
        SirenLink(SirenRelations.GITHUB, URI(githubUri)),
        logoutLink(),
        SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(id)),
        SirenLink(SirenRelations.ORGANIZATIONS, URI(Uris.Organizations.MAIN_PATH))
    )
)