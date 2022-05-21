package pt.isel.ion.teams.home

import pt.isel.ion.teams.classrooms.SimpleClassroomOutputModel
import pt.isel.ion.teams.classrooms.toCompactOutput
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.organizations.OrganizationOutputModel
import pt.isel.ion.teams.organizations.toCompactOutput

fun CollectionModel.toStudentHomeSirenObject(
    classroomList: List<SimpleClassroomOutputModel>
): SirenEntity<CollectionModel> {
    val list = if (classroomList.size > this.pageSize) classroomList.subList(0, this.pageSize) else classroomList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.CLASSROOM),
        entities = list.map {
            EmbeddedEntity(
                properties = it.toCompactOutput(),
                clazz = listOf(SirenClasses.CLASSROOM),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(
                    selfLink(Uris.Classrooms.Classroom.make(it.orgId, it.id)),
                    SirenLink(SirenRelations.TEAMS, Uris.Teams.make(it.orgId, it.id)),
                    SirenLink(SirenRelations.ASSIGNMENTS, Uris.Assignments.make(it.orgId, it.id))
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Home.makePage(pageIndex, pageSize)),
            if (classroomList.size > pageSize)
                nextLink(Uris.Home.makePage(pageIndex + 1, pageSize))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Home.makePage(pageIndex - 1, pageSize))
            else null,
            SirenLink(SirenRelations.ORGANIZATIONS, Uris.Organizations.make()),
            logoutLink()
        )
    )
}

fun CollectionModel.toTeacherHomeSirenObject(
    organizationsList: List<OrganizationOutputModel>
): SirenEntity<CollectionModel> {
    val list = if (organizationsList.size > this.pageSize) organizationsList.subList(0, this.pageSize) else organizationsList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.CLASSROOM),
        entities = list.map {
            EmbeddedEntity(
                properties = it.toCompactOutput(),
                clazz = listOf(SirenClasses.CLASSROOM),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(
                    selfLink(Uris.Organizations.Organization.make(it.id)),
                    SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(it.id)),
                )
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Home.makePage(pageIndex, pageSize)),
            if (organizationsList.size > pageSize)
                nextLink(Uris.Home.makePage(pageIndex + 1, pageSize))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Home.makePage(pageIndex - 1, pageSize))
            else null,
            logoutLink()
        )
    )
}