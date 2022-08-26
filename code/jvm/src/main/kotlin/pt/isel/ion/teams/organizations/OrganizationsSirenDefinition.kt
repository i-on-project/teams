package pt.isel.ion.teams.organizations

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.classrooms.ClassroomCompactOutputModel
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import java.net.URI

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for an organization collection response
 * @param orgsList List of organizations to display
 */
fun CollectionModel.toOrganizationsSirenObject(orgsList: List<OrganizationOutputModel>): SirenEntity<CollectionModel> {
    val list = if (orgsList.size > this.pageSize) orgsList.subList(0, this.pageSize) else orgsList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.ORGANIZATION),
        entities = list.map {
            EmbeddedEntity(
                properties = it.toCompactOutput(),
                rel = listOf(SirenRelations.ITEM),
                clazz = listOf(SirenClasses.ORGANIZATION),
                links = listOf(
                    selfLink(Uris.Organizations.Organization.make(it.id)),
                    SirenLink(SirenRelations.CLASSROOMS,Uris.Classrooms.make(it.id)),
                )
            )
        },
        actions = listOf(
            SirenAction(
                name = "create-organization",
                title = "Create Organization",
                method = HttpMethod.POST,
                href = Uris.Organizations.make(),
                type = MediaType.APPLICATION_JSON,
                fields = listOf(
                    SirenAction.Field(name = "name", type = "string"),
                    SirenAction.Field(name = "description", type = "string"),
                )
            )
        ),
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

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a teacher's organization resource response
 * @param classroomList List of organization's classrooms to display
 */
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
        SirenAction(
            name = "delete-organization",
            title = "Delete Organization",
            method = HttpMethod.DELETE,
            href = Uris.Organizations.Organization.make(id),
        ),
    ),
    links = listOf(
        selfLink(Uris.Organizations.Organization.make(id)),
        homeLink(),
        logoutLink(),
        SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(id)),
        SirenLink(SirenRelations.ORGANIZATIONS, URI(Uris.Organizations.MAIN_PATH))
    )
)