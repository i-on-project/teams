package pt.isel.ion.teams.teacher

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.classrooms.ClassroomsService
import pt.isel.ion.teams.classrooms.toCompactOutput
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Teachers.MAIN_PATH)
class TeachersController(val service: TeachersService, val classService: ClassroomsService) {

    @GetMapping
    fun getTeachers(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize).toTeachersSirenObject(
                service.getTeachers(classId, pageSize, pageIndex)
                    .map { it.toCompactOutput() },
                orgId,
                classId
            )
        )

    @GetMapping(Uris.Teachers.Teacher.PATH)
    fun getTeacher(@PathVariable number: Int, @PathVariable classId: Int, @PathVariable orgId: Int) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            service.getTeacher(number).toOutput()
                .toStudentSirenObject(
                    classService.getAllClassroomsByOrganization(orgId).map { it.toCompactOutput() },
                    classId,
                    orgId
                )
        )

    @PostMapping
    fun createTeacher(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @RequestBody teacher: TeacherInputModel
    ): ResponseEntity<Any> {
        val tch = service.createTeacher(teacher.toDb(classId)).toOutput()

        return ResponseEntity
            .created(Uris.Students.Student.make(orgId, classId, tch.number))
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                tch
            )
    }

    @PutMapping(Uris.Teachers.Teacher.PATH)
    fun updateTeacher(@PathVariable number: Int, @RequestBody teacher: TeacherUpdateModel) =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.updateTeacher(teacher.toDb(number)))

}