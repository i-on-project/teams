package pt.isel.ion.teams.requests

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*


fun CollectionModel.toRequestSirenObject(
    RequestsList: List<RequestsOutputModel>,
    orgId: Int,
    classroomId: Int
): SirenEntity<CollectionModel> {
    val list = if (RequestsList.size > this.pageSize) RequestsList.subList(0, this.pageSize) else RequestsList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.TEAM),
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
                        href = Uris.Requests.Request.make(orgId, classroomId, it.tid),
                        type = MediaType.APPLICATION_JSON
                    ),
                    SirenAction(
                        name = "decline-request",
                        title = "Decline Request",
                        method = HttpMethod.DELETE,
                        href = Uris.Teams.Team.make(orgId, classroomId, it.tid)
                    )
                ),
                links = listOf(
                    selfLink(Uris.Requests.Request.make(orgId, classroomId, it.tid)),
                    SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, classroomId, it.tid))
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Requests.make(orgId, classroomId)),
            if (RequestsList.size > pageSize)
                nextLink(Uris.Requests.makePage(pageIndex + 1, pageSize, orgId, classroomId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Requests.makePage(pageIndex - 1, pageSize, orgId, classroomId))
            else null,
            SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classroomId)),
            homeLink(),
            logoutLink()
        )
    )
}