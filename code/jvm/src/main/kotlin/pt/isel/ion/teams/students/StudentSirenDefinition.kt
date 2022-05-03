package pt.isel.ion.teams.students

import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.common.Uris


fun CollectionModel.toStudentSirenObject(
    studentList: List<StudentCompactOutputModel>,
    orgId: Int,
    cId: Int
): SirenEntity<CollectionModel> {
    val list = if (studentList.size > this.pageSize) studentList.subList(0, this.pageSize) else studentList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.STUDENT),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.STUDENT),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Students.Student.make(orgId, cId, it.number)))
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Classrooms.make(orgId)),
            if (studentList.size > pageSize)
                nextLink(Uris.Students.makePage(pageIndex + 1, pageSize, orgId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Students.makePage(pageIndex - 1, pageSize, orgId))
            else null,
            SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
            homeLink(),
            logoutLink(),
        )
    )
}