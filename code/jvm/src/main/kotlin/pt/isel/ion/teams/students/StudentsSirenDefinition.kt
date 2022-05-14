package pt.isel.ion.teams.students

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.teams.TeamsCompactOutputModel


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
            selfLink(Uris.Students.make(orgId,cId)),
            if (studentList.size > pageSize)
                nextLink(Uris.Students.makePage(pageIndex + 1, pageSize, cId, orgId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Students.makePage(pageIndex - 1, pageSize, cId, orgId))
            else null,
            SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, cId)),
            homeLink(),
            logoutLink(),
        )
    )
}


fun CompleteStudentOutputModel.toTeacherSirenObject(
    teamsList: List<TeamsCompactOutputModel>,
    classId: Int,
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.STUDENT),
    entities = teamsList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.TEAM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Teams.Team.make(orgId, classId, it.id)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-student",
            title = "Update Student",
            method = HttpMethod.PUT,
            href = Uris.Students.Student.make(orgId, classId, number),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string"),
                SirenAction.Field(name = "tId", type = "number"),
                SirenAction.Field(name = "cId", type = "number")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.Students.Student.make(orgId, classId, number)),
        homeLink(),
        SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.Classroom.make(orgId, classId)),
        logoutLink()
    )
)


fun CompleteStudentOutputModel.toStudentSirenObject(
    teamList: List<TeamsCompactOutputModel>,
    classId: Int,
    orgId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.STUDENT),
    entities = teamList.map {
        EmbeddedEntity(
            properties = it,
            clazz = listOf(SirenClasses.TEAM),
            rel = listOf(SirenRelations.ITEM),
            links = listOf(selfLink(Uris.Teams.Team.make(orgId, classId, it.id)))
        )
    },
    actions = listOf(
        SirenAction(
            name = "update-student",
            title = "Update Student",
            method = HttpMethod.PUT,
            href = Uris.Students.Student.make(orgId, classId, number),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "name", type = "string")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.Students.Student.make(orgId, classId, number)),
        SirenLink(SirenRelations.CLASSROOMS, Uris.Classrooms.Classroom.make(orgId, classId)),
        homeLink(),
        logoutLink()
    )
)
