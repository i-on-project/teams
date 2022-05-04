package pt.isel.ion.teams.teacher

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.common.Uris


fun CollectionModel.toTeachersSirenObject(
    teacherList: List<TeacherCompactOutputModel>,
    orgId: Int,
    cId: Int
): SirenEntity<CollectionModel> {
    val list = if (teacherList.size > this.pageSize) teacherList.subList(0, this.pageSize) else teacherList
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
            if (teacherList.size > pageSize)
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


fun TeacherOutputModel.toTeacherSirenObject(
    teacherList: List<TeacherCompactOutputModel>,
    classId: Int,
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.STUDENT),
    entities = teacherList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.TEAM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Students.Student.make(orgId, classId, number)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-student",
            title = "Update Student",
            method = HttpMethod.PUT,
            href = Uris.Students.Student.make(orgId,classId,number),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "tId", type = "number"),
                SirenAction.Field(name = "cId", type = "number")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.Students.Student.make(orgId,classId,number)),
        homeLink(),
        SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
        logoutLink()
    )
)


fun TeacherOutputModel.toStudentSirenObject(
    teacherList: List<TeacherCompactOutputModel>,
    classId: Int,
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.STUDENT),
    entities = teacherList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.TEAM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Students.Student.make(orgId, classId, number)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-student",
            title = "Update Student",
            method = HttpMethod.PUT,
            href = Uris.Students.Student.make(orgId,classId,number),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.Students.Student.make(orgId,classId,number)),
        SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
        homeLink(),
        logoutLink()
    )
)
