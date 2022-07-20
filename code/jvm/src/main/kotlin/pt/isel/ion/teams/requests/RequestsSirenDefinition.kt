package pt.isel.ion.teams.requests

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for a request collection response
 * @param requestsList List of requests to display
 * @param orgId Request's organization id
 * @param classId Request   's classroom id
 */
fun CollectionModel.toRequestSirenObject(
    requestsList: List<RequestsOutputModel>,
    orgId: Int,
    classId: Int
): SirenEntity<CollectionModel> {
    val list = if (requestsList.size > this.pageSize) requestsList.subList(0, this.pageSize) else requestsList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.REQUEST),
        entities = list.map {
            EmbeddedEntity(
                properties = it.toCompactOutput(),
                clazz = listOf(SirenClasses.REQUEST),
                rel = listOf(SirenRelations.ITEM),
                actions = listOf(
                    SirenAction(
                        name = "accept-request",
                        title = "Accept Request",
                        method = HttpMethod.PUT,
                        href = Uris.Requests.Request.make(orgId, classId, it.tid),
                        type = MediaType.APPLICATION_JSON
                    ),
                    SirenAction(
                        name = "decline-request",
                        title = "Decline Request",
                        method = HttpMethod.DELETE,
                        href = Uris.Teams.Team.make(orgId, classId, it.tid)
                    )
                ),
                links = listOf(
                    selfLink(Uris.Requests.Request.make(orgId, classId, it.tid)),
                    SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, classId, it.tid))
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Requests.make(orgId, classId)),
            if (requestsList.size > pageSize)
                nextLink(Uris.Requests.makePage(pageIndex + 1, pageSize, orgId, classId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Requests.makePage(pageIndex - 1, pageSize, orgId, classId))
            else null,
            SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classId)),
            homeLink(),
            logoutLink()
        )
    )
}