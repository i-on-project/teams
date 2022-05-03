package pt.isel.ion.teams.students

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.CollectionModel
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.classrooms.toClassroomSirenObject
import pt.isel.ion.teams.classrooms.toCompactOutput
import pt.isel.ion.teams.common.Uris


@RestController
@RequestMapping(Uris.Students.MAIN_PATH)
class StudentsController(val studentsService: StudentsService) {

    @GetMapping(Uris.Students.FromClassroom.PATH)
    fun getAllStudentsByClassroom(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize).toStudentSirenObject(
                studentsService.getAllStudentsByClassroom(pageSize, pageIndex, classId)
                    .map { it.toCompactOutput() },
                orgId,
                classId
            )
        )

    @GetMapping(Uris.Students.FromTeam.PATH)
    fun getAllStudentsByTeam(
        @PathVariable teamId: Int,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize).toStudentSirenObject(
                studentsService.getAllStudentsByTeam(pageSize, pageIndex, teamId)
                    .map { it.toCompactOutput() },
                orgId,
                classId
            )
        )

    @PostMapping(Uris.Students.FromClassroom.PATH)
    fun createStudent(@RequestBody student: StudentInputModel) =
        studentsService.createStudent(student.toDb())

    @PutMapping(Uris.Students.Student.PATH)
    fun updateStudent(@PathVariable number: Int, @RequestBody student: StudentUpdateModel) =
        studentsService.updateStudent(student.toDb(number))

    @PutMapping(Uris.Students.Student.PATH)
    fun associateStudentToTeam(@PathVariable number: Int, student: StudentAssociationInputModel) =
        studentsService.associateStudentToTeam(student.toDb(number))

    @PutMapping(Uris.Students.Student.PATH)
    fun dissociateStudentFromTeam(@PathVariable number: Int) = studentsService.dissociateStudentFromTeam(number)


}