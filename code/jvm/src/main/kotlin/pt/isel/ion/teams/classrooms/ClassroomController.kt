package pt.isel.ion.teams.classrooms

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.teams.TeamsService
import pt.isel.ion.teams.teams.toCompactOutput

@RestController
@RequestMapping(Uris.Classrooms.MAIN_PATH)
class ClassroomController(
    val classroomService: ClassroomService,
    val teamsService: TeamsService
) {

    @GetMapping
    fun getAllClassroomsByOrganization(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize).toClassroomSirenObject(
                classroomService.getAllClassroomsByOrganizationWithPaging(pageSize, pageIndex, orgId).map{ it.toOutput()},
                orgId
            )
        )

    @GetMapping(Uris.Classrooms.Classroom.PATH)
    fun getClassroom(
        @PathVariable orgId: Int,
        @PathVariable classId: Int
    ): ResponseEntity<Any> {
        val classroom = classroomService.getClassroom(classId).toOutput()
        val teams = teamsService.getAllTeamsOfClassroom(10, 0, classId)

        //TODO Detect if user is student or teacher

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
        //TODO retrieve real githubURI and avatarURI
        val classroom = classroomService.createClassroom(classroomInputModel.toDb(orgId,"example","example")).toOutput()

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
        .body(classroomService.updateClassroom(classroomUpdateModel.toDb(classId)).toOutput())

    @DeleteMapping(Uris.Classrooms.Classroom.PATH)
    fun deleteClassroom(@PathVariable classId: Int): ResponseEntity<Any> {
        classroomService.deleteClassroom(classId)
        return ResponseEntity
            .ok()
            .body(null)
    }
}