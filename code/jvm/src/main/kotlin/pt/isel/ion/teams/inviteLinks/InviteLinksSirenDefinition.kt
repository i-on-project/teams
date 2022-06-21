package pt.isel.ion.teams.inviteLinks

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for an invite link collection response
 * @param listOfLinks Links to display
 * @param orgId Invite link's organization id
 * @param classId Invite link's classroom id
 */
fun CollectionModel.toInviteLinksSirenObject(
    listOfLinks: List<InviteLinksOutputModel>,
    orgId: Int,
    classId: Int
): SirenEntity<CollectionModel> {
    val list = if (listOfLinks.size > this.pageSize) listOfLinks.subList(0, this.pageSize) else listOfLinks
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.INVITE_LINK),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.INVITE_LINK),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(
                    selfLink(Uris.InviteLinks.InviteLink.make(orgId, it.cid, it.code)),
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Classrooms.make(orgId)),
            if (list.size > pageSize)
                nextLink(Uris.InviteLinks.makePage(pageIndex + 1, pageSize, orgId, classId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.InviteLinks.makePage(pageIndex - 1, pageSize, orgId, classId))
            else null,
            SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classId)),
            homeLink(),
            logoutLink(),
        )
    )
}

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for an invite link response
 * @param orgId Invite link's organization id
 * @param classId Invite link's classroom id
 */
fun InviteLinksOutputModel.toSirenObject(
    classId: Int,
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.INVITE_LINK),
    actions = listOf(
        SirenAction(
            name = "create-team",
            title = "Create Team",
            method = HttpMethod.POST,
            href = Uris.Teams.make(orgId, classId),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.InviteLinks.InviteLink.make(orgId, classId, code)),
        homeLink(),
        logoutLink(),
        //TODO: signup()
    )
)
