package pt.isel.ion.teams.teacher

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class TeacherService(val jdbi: Jdbi) {

    fun getTeachers(classroomId: Int) = jdbi.onDemand(TeacherDAO::class.java).getTeachers(classroomId)

    fun getTeacher(number: Int) = jdbi.onDemand(TeacherDAO::class.java).getTeacher(number)

    fun createTeacher(teacher: TeacherDbWrite) = jdbi.onDemand(TeacherDAO::class.java).createTeacher(teacher)

    fun updateTeacher(teacher: TeacherDbUpdate) = jdbi.onDemand(TeacherDAO::class.java).updateTeacher(teacher)
}