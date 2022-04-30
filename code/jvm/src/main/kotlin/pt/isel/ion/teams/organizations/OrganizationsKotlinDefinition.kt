package pt.isel.ion.teams.organizations

import pt.isel.daw.project.common.siren.*
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

fun OrganizationOutputModel.toStudentSirenObject() = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.ORGANIZATION),
    //TODO Review documentation to implement siren
)