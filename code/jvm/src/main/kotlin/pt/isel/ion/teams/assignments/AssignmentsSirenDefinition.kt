package pt.isel.ion.teams.assignments

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.deliveries.DeliveryCompactOutputModel

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for an assignment collection response
 * @param assignmentList List of assignments to display
 * @param orgId Assignment's organization id
 * @param classId Assignment's classroom id
 */
fun CollectionModel.toAssignmentsSirenObject(
    assignmentList: List<AssignmentCompactOutputModel>,
    orgId: Int,
    classId: Int
): SirenEntity<CollectionModel> {
    val list = if (assignmentList.size > this.pageSize) assignmentList.subList(0, this.pageSize) else assignmentList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.ASSIGNMENT),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.ASSIGNMENT),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Assignments.Assignment.make(orgId, classId, it.id)))
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Assignments.make(orgId, classId)),
            if (assignmentList.size > pageSize)
                nextLink(Uris.Assignments.makePage(pageIndex + 1, pageSize, orgId, classId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Assignments.makePage(pageIndex - 1, pageSize, orgId, classId))
            else null,
            SirenLink(SirenRelations.ORGANIZATIONS, Uris.Organizations.make()),
            SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(orgId)),
            homeLink(),
            logoutLink(),
        )
    )
}

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a student's assignment resource response
 * @param deliveriesList List of deliveries to display
 * @param orgId orgId Assignment's organization id
 * @param classId Assignment's classroom id
 */
fun AssignmentOutputModel.toStudentSirenObject(
    deliveriesList: List<DeliveryCompactOutputModel>,
    orgId: Int,
    classId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.ASSIGNMENT),
    entities = deliveriesList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.DELIVERY),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Deliveries.Delivery.make(orgId, classId, this.id, it.id)))
        )
    },
    links = listOf(
        selfLink(Uris.Assignments.Assignment.make(orgId, classId, id)),
        SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.Classroom.make(orgId,classId)),
        SirenLink(SirenRelations.ASSIGNMENTS, Uris.Assignments.make(orgId, classId)),
        homeLink(),
        logoutLink()
    )
)

/**
 * Siren definition for a student's assignment resource response
 * @param deliveriesList List of deliveries to display
 * @param orgId orgId Assignment's organization id
 * @param classId Assignment's classroom id
 */
fun AssignmentOutputModel.toTeacherSirenObject(
    deliveriesList: List<DeliveryCompactOutputModel>,
    orgId: Int,
    classId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.ASSIGNMENT),
    entities = deliveriesList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.DELIVERY),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Deliveries.Delivery.make(orgId, classId, this.id, it.id)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-assignment",
            title = "Update Assignment",
            method = HttpMethod.PUT,
            href = Uris.Assignments.Assignment.make(orgId, classId, id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "releaseDate", type = "string"),
                SirenAction.Field(name = "cId", type = "number"),
                SirenAction.Field(name = "description", type = "string"),
            )
        ),
        SirenAction(
            name = "delete-assignment",
            title = "Delete Assignment",
            method = HttpMethod.DELETE,
            href = Uris.Assignments.Assignment.make(orgId, classId, id)
        ),
    ),
    links = listOf(
        selfLink(Uris.Assignments.Assignment.make(orgId, classId, id)),
        SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.Classroom.make(orgId, classId)),
        SirenLink(SirenRelations.ASSIGNMENTS, Uris.Deliveries.make(orgId, classId, id)),
        homeLink(),
        logoutLink()
    )
)