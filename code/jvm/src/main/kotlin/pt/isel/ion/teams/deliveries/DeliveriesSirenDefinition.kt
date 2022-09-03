package pt.isel.ion.teams.deliveries

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.tags.TagWithTeamRepoDbRead

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for a delivery collection response
 * @param deliveryList List of deliveries to display
 * @param orgId Delivery's organization id
 * @param classId Delivery's classroom id
 * @param assId Delivery's assignment id
 */
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

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a student's delivery resource response
 * @param orgId Delivery's organization id
 * @param classId Delivery's classroom id
 * @param assId Delivery's assignment id
 */
fun DeliveryOutputModel.toStudentSirenObject(
    orgId: Int,
    classId: Int,
    assId: Int,
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.DELIVERY),
    links = listOf(
        selfLink(Uris.Deliveries.Delivery.make(orgId, classId, assId, id)),
        homeLink(),
        SirenLink(SirenRelations.DELIVERIES, Uris.Deliveries.make(orgId, classId, assId)),
        logoutLink()
    )
)

/**
 * Siren definition for a teacher's delivery resource response
 * @param tags List of tags associated with the delivery
 * @param orgId Delivery's organization id
 * @param classId Delivery's classroom id
 * @param assId Delivery's assignment id
 */
fun DeliveryOutputModel.toTeacherSirenObject(
    tags: List<TagWithTeamRepoDbRead>,
    orgId: Int,
    classId: Int,
    assId: Int,
): SirenEntity<DeliveryOutputModel> {

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.DELIVERY),
        entities = tags.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.TAG),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Tags.Tag.make(orgId, classId, it.teamId, it.repoId, it.id)))
            )
        },
        actions = listOf(
            SirenAction(
                name = "update-delivery",
                title = "Update Delivery",
                method = HttpMethod.PUT,
                href = Uris.Deliveries.Delivery.make(orgId, classId, assId, id),
                type = MediaType.APPLICATION_JSON,
                fields = listOf(
                    SirenAction.Field(name = "date", type = "datetime-local"),
                )
            ),
            SirenAction(
                name = "delete-delivery",
                title = "Delete Delivery",
                method = HttpMethod.DELETE,
                href = Uris.Deliveries.Delivery.make(orgId, classId, assId, id)
            ),
        ),
        links = listOf(
            selfLink(Uris.Deliveries.Delivery.make(orgId, classId, assId, id)),
            homeLink(),
            SirenLink(SirenRelations.DELIVERIES, Uris.Deliveries.make(orgId, classId, assId)),
            logoutLink()
        )
    )
}