package pt.isel.ion.teams.classrooms

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.authentication.AuthenticationService
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.teams.TeamsService
import pt.isel.ion.teams.teams.toCompactOutput

@RestController
@RequestMapping(Uris.Classrooms.MAIN_PATH)
class ClassroomsController(
    val classroomsService: ClassroomsService,
    val teamsService: TeamsService,
    val authService: AuthenticationService
) {

    @GetMapping
    fun getAllClassroomsByOrganization(
        @CookieValue session: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int
    ): ResponseEntity<Any> {
        val number = authService.getNumber(session)

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                CollectionModel(pageIndex, pageSize).toClassroomSirenObject(
                    classroomsService.getAllClassroomsByOrganizationWithPaging(pageSize, pageIndex, orgId, number)
                        .map { it.toOutput() },
                    orgId
                )
            )
    }

    @GetMapping(Uris.Classrooms.Classroom.PATH)
    fun getClassroom(
        @PathVariable orgId: Int,
        @PathVariable classId: Int
    ): ResponseEntity<Any> {
        val classroom = classroomsService.getClassroom(classId).toOutput()
        val teams = teamsService.getAllTeamsOfClassroom(10, 0, classId)

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                classroom.toTeacherSirenObject(teams.map { it.toCompactOutput() }, orgId)
            )
    }

    @PostMapping
    fun createClassroom(
        @PathVariable orgId: Int,
        @RequestBody classroomInputModel: ClassroomInputModel
    ): ResponseEntity<Any> {
        val classroom =
            classroomsService.createClassroom(classroomInputModel.toDb(orgId)).toOutput()

        return ResponseEntity
            .created(Uris.Classrooms.Classroom.make(orgId, classroom.id))
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                classroom
            )
    }

    @PutMapping(Uris.Classrooms.Classroom.PATH)
    fun updateClassroom(
        @PathVariable classId: Int,
        @RequestBody classroomUpdateModel: ClassroomUpdateModel
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(classroomsService.updateClassroom(classroomUpdateModel.toDb(classId)).toOutput())

    @DeleteMapping(Uris.Classrooms.Classroom.PATH)
    fun deleteClassroom(@PathVariable classId: Int): ResponseEntity<Any> {
        classroomsService.deleteClassroom(classId)
        return ResponseEntity
            .ok()
            .body(null)
    }
}