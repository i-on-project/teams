package pt.isel.ion.teams.students

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.teams.TeamsCompactOutputModel

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

fun CollectionModel.toStudentTeamsSirenObject(
    studentList: List<StudentTeamsOutputModel>
): SirenEntity<CollectionModel> {

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.STUDENT),
        entities = studentList.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.STUDENT),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Students.Student.make(it.orgId, it.classId, it.number)))
            )
        },
        links = listOfNotNull(
            homeLink(),
            logoutLink(),
        )
    )
}


/**
 * Siren definition for a student list by team collection response
 * @param studentList List of deliveries to display
 * @param orgId Student's organization id
 * @param cId Student's classroom id
 * @param teamId Student's assignment id
 */
fun CollectionModel.toStudentByTeamSirenObject(
    studentList: List<StudentCompactOutputModel>,
    orgId: Int,
    cId: Int,
    teamId: Int
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
            selfLink(Uris.Students.FromTeam.make(orgId, cId, teamId)),
            if (studentList.size > pageSize)
                nextLink(Uris.Students.makePage(pageIndex + 1, pageSize, cId, orgId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Students.makePage(pageIndex - 1, pageSize, cId, orgId))
            else null,
            SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, cId, teamId)),
            homeLink(),
            logoutLink(),
        )
    )
}

/**
 * Siren definition for a student list by classroom collection response
 * @param studentList List of deliveries to display
 * @param orgId Student's organization id
 * @param cId Student's classroom id
 */
fun CollectionModel.toStudentByClassroomSirenObject(
    studentList: List<StudentCompactOutputModel>,
    orgId: Int,
    cId: Int,
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
            selfLink(Uris.Students.FromClassroom.make(orgId, cId)),
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

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a student's resource response (for teachers)
 * @param teamsList List of teams of a student to display
 * @param classId Student's classroom id
 * @param orgId Organization's assignment id
 */
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
                SirenAction.Field(name = "name", type = "string")
            )
        )
    ),
    links = listOf(
        selfLink(Uris.Students.Student.make(orgId, classId, number)),
        homeLink(),
        SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classId)),
        logoutLink()
    )
)

/**
 * Siren definition for a student's resource response (for students)
 * @param teamsList List of teams of a student to display
 * @param classId Student's classroom id
 * @param orgId Organization's assignment id
 */
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
        SirenLink(SirenRelations.CLASSROOM, Uris.Classrooms.Classroom.make(orgId, classId)),
        homeLink(),
        logoutLink()
    )
)
