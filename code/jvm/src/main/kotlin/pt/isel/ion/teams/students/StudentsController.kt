package pt.isel.ion.teams.students

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris


@RestController
@RequestMapping(Uris.Students.MAIN_PATH)
class StudentsController(val studentsService: StudentsService) {

    @GetMapping(Uris.Students.FromClassroom.PATH)
    fun getAllStudentsByClassroom(@PathVariable classroomId: Int): List<StudentOutputModel> =
        studentsService.getAllStudentsByClassroom(classroomId).map { it.toOutput() }

    @GetMapping(Uris.Students.FromTeam.PATH)
    fun getAllStudentsByTeam(@PathVariable teamId: Int): List<StudentOutputModel> =
        studentsService.getAllStudentsByTeam(teamId).map { it.toOutput() }

    @PostMapping(Uris.Students.FromClassroom.PATH)
    fun createStudent(@RequestBody student: StudentInputModel) =
        studentsService.createStudent(student.toDb())


    @PutMapping(Uris.Students.Student.PATH)
    fun updateStudent(@PathVariable number: Int, @RequestBody student: StudentUpdateModel) =
        studentsService.updateStudent(student.toDb(number))

}