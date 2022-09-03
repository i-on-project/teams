package pt.isel.ion.teams.teacher

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.classrooms.ClassroomCompactOutputModel
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for a teacher collection response
 * @param teacherList List of tags to display
 * @param orgId Teacher's organization id
 * @param classId Teacher's classroom id
 */
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

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a teacher resource response
 * @param classroomsList List of classrooms associated with a given teacher
 * @param orgId Teacher's organization id
 * @param classId Teacher's classroom id
 */
fun TeacherCompactOutputModel.toTeacherSirenObject(
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
            name = "update-teacher",
            title = "Update Teacher",
            method = HttpMethod.PUT,
            href = Uris.Teachers.Teacher.make(orgId, classId, number),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "text"),
                SirenAction.Field(name = "email", type = "text"),
                SirenAction.Field(name = "office", type = "text"),
                SirenAction.Field(name = "cId", type = "number")
            )
        ),
        SirenAction(
            name = "add-teacher",
            title = "Add Teacher",
            method = HttpMethod.POST,
            href = Uris.Teachers.Teacher.make(orgId, classId, number),
            type = MediaType.APPLICATION_JSON,
            fields = listOf()
        ),
        SirenAction(
            name = "remove-teacher",
            title = "Remove Teacher",
            method = HttpMethod.DELETE,
            href = Uris.Teachers.Teacher.make(orgId, classId, number),
            type = MediaType.APPLICATION_JSON
        )
    ),
    links = listOf(
        selfLink(Uris.Teachers.Teacher.make(orgId, classId, number)),
        SirenLink(SirenRelations.ORGANIZATION, Uris.Organizations.Organization.make(orgId)),
        homeLink(),
        logoutLink()
    )
)

/**
 * Siren definition for a teacher resource response
 * @param classroomsList List of classrooms associated with a given teacher
 * @param orgId Teacher's organization id
 * @param classId Teacher's classroom id
 */
fun TeacherCompactOutputModel.toStudentSirenObject(
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
