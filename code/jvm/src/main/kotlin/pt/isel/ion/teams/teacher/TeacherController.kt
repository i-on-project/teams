package pt.isel.ion.teams.teacher

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Teachers.PATH)
class TeacherController(val service: TeacherService) {

    @GetMapping()
    fun getTeachers(@PathVariable classroomId: Int) = service.getTeachers(classroomId)

    @GetMapping(Uris.Teachers.Teacher.PATH)
    fun getTeacher(@PathVariable number: Int) = service.getTeacher(number)
}