package pt.isel.ion.teams.students

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.teams.TeamsService
import pt.isel.ion.teams.teams.toCompactOutput

@RestController
@RequestMapping(Uris.Students.MAIN_PATH)
class StudentsController(val studentsService: StudentsService, val teamsService: TeamsService) {

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
            CollectionModel(pageIndex, pageSize).toStudentByClassroomSirenObject(
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
            CollectionModel(pageIndex, pageSize).toStudentByTeamSirenObject(
                studentsService.getAllStudentsByTeam(teamId, pageSize, pageIndex)
                    .map { it.toCompactOutput() },
                orgId,
                classId,
                teamId
            )
        )

    @GetMapping(Uris.Students.Student.PATH)
    fun getStudent(@PathVariable number: Int, @PathVariable classId: Int, @PathVariable orgId: Int) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            studentsService.getStudent(number).toOutput()
                .toStudentSirenObject(
                    teamsService.getAllTeamsOfClassroom(10, 0, classId).map { it.toCompactOutput() },
                    classId,
                    orgId
                )
        )

    @PostMapping(Uris.Students.FromClassroom.PATH)
    fun createStudent(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @RequestBody student: StudentInputModel
    ): ResponseEntity<Any> {
        val std = studentsService.createStudent(student.toDb()).toOutput()

        return ResponseEntity
            .created(Uris.Students.Student.make(orgId, classId, std.number))
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                std
            )
    }

    @PutMapping(Uris.Students.Student.PATH)
    fun updateStudent(@PathVariable number: Int, @RequestBody student: StudentUpdateModel) =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(studentsService.updateStudent(student.toDb(number)).toOutput())

    @PostMapping(Uris.Students.FromTeam.PATH)
    fun addStudent(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int,
        @RequestBody student: StudentClassInfoInputModel
    ): ResponseEntity<Any> {
        val std = studentsService.addStudent(student.toDb(teamId,classId)).toOutput()

        return ResponseEntity
            .created(Uris.Students.Student.make(orgId, classId, std.number))
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                std
            )
    }

    @DeleteMapping(Uris.Students.Student.PATH)
    fun removeStudent(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable number: Int
    ): ResponseEntity<Any> {
        studentsService.removeStudent(number, classId)
        return ResponseEntity
            .ok()
            .body(null)
    }



}

