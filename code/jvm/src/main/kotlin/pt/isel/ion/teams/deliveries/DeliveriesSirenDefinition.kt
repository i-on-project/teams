package pt.isel.ion.teams.deliveries

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.tags.TagWithTeamRepoDbRead


fun CollectionModel.toDeliveriesSirenObject(
    deliveryList: List<DeliveryCompactOutputModel>,
    orgId: Int,
    classId: Int,
    assId: Int
): SirenEntity<CollectionModel> {
    val list = if (deliveryList.size > this.pageSize) deliveryList.subList(0, this.pageSize) else deliveryList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.DELIVERY),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.DELIVERY),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Deliveries.Delivery.make(orgId, classId, assId, it.id)))
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Deliveries.make(orgId, classId, assId)),
            if (deliveryList.size > pageSize)
                nextLink(Uris.Deliveries.makePage(pageIndex + 1, pageSize, orgId, classId, assId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Deliveries.makePage(pageIndex - 1, pageSize, orgId, classId, assId))
            else null,
            SirenLink(SirenRelations.ORGANIZATIONS, Uris.Organizations.make()),
            SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(orgId)),
            SirenLink(SirenRelations.DELIVERIES, Uris.Deliveries.make(orgId, classId, assId)),
            homeLink(),
            logoutLink(),
        )
    )
}

fun DeliveryOutputModel.toStudentSirenObject(
    orgId: Int,
    classId: Int,
    assId: Int,
) = SirenEntity(
    properties = this,
    links = listOf(
        selfLink(Uris.Deliveries.Delivery.make(orgId, classId, assId, id)),
        homeLink(),
        SirenLink(SirenRelations.DELIVERIES, Uris.Deliveries.make(orgId, classId, assId)),
        logoutLink()
    )
)

fun DeliveryOutputModel.toTeacherSirenObject(
    tags: List<TagWithTeamRepoDbRead>,
    orgId: Int,
    classId: Int,
    assId: Int,
): SirenEntity<DeliveryOutputModel> {

    return SirenEntity(
        properties = this,
        actions = listOf(
            SirenAction(
                name = "update-delivery",
                title = "Update Delivery",
                method = HttpMethod.PUT,
                href = Uris.Deliveries.Delivery.make(orgId, classId, assId, id),
                type = MediaType.APPLICATION_JSON,
                fields = listOf(
                    SirenAction.Field(name = "date", type = "string"),
                )
            ),
            SirenAction(
                name = "delete-delivery",
                title = "Delete Delivery",
                method = HttpMethod.DELETE,
                href = Uris.Deliveries.Delivery.make(orgId, classId, assId, id)
            ),
        ),
        entities = tags.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.TAG),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Tags.Tag.make(orgId, classId, it.teamId, it.repoId, it.id)))
            )
        },
        links = listOf(
            selfLink(Uris.Deliveries.Delivery.make(orgId, classId, assId, id)),
            homeLink(),
            SirenLink(SirenRelations.DELIVERIES, Uris.Deliveries.make(orgId, classId, assId)),
            logoutLink()
        )
    )
}