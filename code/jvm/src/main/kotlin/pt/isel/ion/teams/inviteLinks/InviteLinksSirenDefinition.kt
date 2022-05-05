package pt.isel.ion.teams.inviteLinks

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.common.Uris

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
        selfLink(Uris.InviteLinks.InviteLink.make(orgId, classId,code)),
        homeLink(),
        logoutLink(),
        //TODO: signup()
    )
)
