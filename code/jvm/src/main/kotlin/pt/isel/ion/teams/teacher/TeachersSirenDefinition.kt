package pt.isel.ion.teams.teacher

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.classrooms.ClassroomCompactOutputModel
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*


fun CollectionModel.toTeachersSirenObject(
    teacherList: List<TeacherCompactOutputModel>,
    orgId: Int,
    classId: Int
): SirenEntity<CollectionModel> {
    val list = if (teacherList.size > this.pageSize) teacherList.subList(0, this.pageSize) else teacherList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.TEACHER),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.TEACHER),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Teachers.Teacher.make(orgId, classId, it.number)))
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Teachers.make(orgId, classId)),
            if (teacherList.size > pageSize)
                nextLink(Uris.Teachers.makePage(pageIndex + 1, pageSize, orgId, classId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Teachers.makePage(pageIndex - 1, pageSize, orgId, classId))
            else null,
            SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.make(orgId)),
            homeLink(),
            logoutLink(),
        )
    )
}


fun CompleteTeacherOutputModel.toTeacherSirenObject(
    classroomsList: List<ClassroomCompactOutputModel>,
    classId: Int,
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEACHER),
    entities = classroomsList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.CLASSROOM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Classrooms.Classroom.make(orgId, it.id)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-student",
            title = "Update Student",
            method = HttpMethod.PUT,
            href = Uris.Teachers.Teacher.make(orgId, classId, number),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "email", type = "string"),
                SirenAction.Field(name = "office", type = "string"),
                SirenAction.Field(name = "cId", type = "number")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.Teachers.Teacher.make(orgId, classId, number)),
        SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
        homeLink(),
        logoutLink()
    )
)


fun CompleteTeacherOutputModel.toStudentSirenObject(
    classroomsList: List<ClassroomCompactOutputModel>,
    classId: Int,
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TEACHER),
    entities = classroomsList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.CLASSROOM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Classrooms.Classroom.make(orgId, it.id)))
        )
    },
    links = listOf(
        selfLink(Uris.Teachers.Teacher.make(orgId, classId, number)),
        SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
        homeLink(),
        logoutLink()
    )
)
