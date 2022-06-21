package pt.isel.ion.teams.home

import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*

//TODO: Home is not a collection

fun StudentHomeSirenObject(): SirenEntity<CollectionModel> =
    SirenEntity(
        clazz = listOf(SirenClasses.HOME),
        links = listOfNotNull(
            selfLink(Uris.Home.make()),
            SirenLink(SirenRelations.ORGANIZATIONS, Uris.Organizations.make()),
            //SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(x)),
            logoutLink()
        )
    )

fun TeacherHomeSirenObject(): SirenEntity<CollectionModel> =
    SirenEntity(
        clazz = listOf( SirenClasses.HOME),
        links = listOfNotNull(
            selfLink(Uris.Home.make()),
            SirenLink(SirenRelations.ORGANIZATIONS, Uris.Organizations.make()),
            logoutLink()
        )
    )
