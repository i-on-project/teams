package pt.isel.ion.teams.deliveries

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.common.Uris

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
    orgId: Int,
    classId: Int,
    assId: Int,
) = SirenEntity(
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
    links = listOf(
        selfLink(Uris.Deliveries.Delivery.make(orgId, classId, assId, id)),
        homeLink(),
        SirenLink(SirenRelations.DELIVERIES, Uris.Deliveries.make(orgId, classId, assId)),
        logoutLink()
    )
)