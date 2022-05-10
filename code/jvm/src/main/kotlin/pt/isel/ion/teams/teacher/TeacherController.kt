package pt.isel.ion.teams.teacher

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.classrooms.ClassroomService
import pt.isel.ion.teams.classrooms.toCompactOutput
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Teachers.MAIN_PATH)
class TeacherController(val service: TeacherService, val classService: ClassroomService) {

    @GetMapping()
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
            service.getTeacher(number).toOutput(classId)
                .toStudentSirenObject(
                    classService.getAllClassroomsByOrganization(orgId).map { it.toCompactOutput() },
                    classId,
                    orgId
                )
        )

    @PostMapping
    fun createTeacher(@RequestBody teacher: TeacherInputModel) = service.createTeacher(teacher.toDb())

    @PutMapping(Uris.Teachers.Teacher.PATH)
    fun updateTeacher(@PathVariable number: Int, @RequestBody teacher: TeacherUpdateModel) =
        service.updateTeacher(teacher.toDb(number))
}