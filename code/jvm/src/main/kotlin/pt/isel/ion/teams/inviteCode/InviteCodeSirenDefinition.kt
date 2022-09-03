package pt.isel.ion.teams.inviteCode

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
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.INVITE_CODE),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.INVITE_CODE),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(
                    selfLink(Uris.InviteCodes.InviteCode.make(it.code)),
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Classrooms.make(orgId)),
            if (list.size > pageSize)
                nextLink(Uris.InviteCodes.makePage(pageIndex + 1, pageSize, orgId, classId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.InviteCodes.makePage(pageIndex - 1, pageSize, orgId, classId))
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
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.INVITE_CODE),
    actions = listOf(
        SirenAction(
            name = "create-team",
            title = "Create Team",
            method = HttpMethod.POST,
            href = Uris.Teams.make(orgId, this.cid),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "text")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.InviteCodes.InviteCode.make(code)),
        homeLink(),
        logoutLink(),
        SirenLink(SirenRelations.TEAMS, Uris.Teams.make(orgId, this.cid))
    )
)
