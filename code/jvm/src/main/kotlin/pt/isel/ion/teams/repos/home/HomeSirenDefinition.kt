package pt.isel.ion.teams.repos.home

import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*


fun HomeSirenObject(): SirenEntity<CollectionModel> =
    SirenEntity(
        clazz = listOf(SirenClasses.HOME),
        links = listOfNotNull(
            selfLink(Uris.Home.make()),
            SirenLink(SirenRelations.ORGANIZATIONS, Uris.Organizations.make()),
            logoutLink()
        )
    )

