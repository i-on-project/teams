package pt.isel.ion.teams.classrooms

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class ClassroomService(val jdbi: Jdbi) {

    fun getAllClassroomsByOrganization(orgId: Int) =
        jdbi.onDemand(ClassroomDAO::class.java).getAllClassroomsByOrganization(orgId)

    fun getClassroom(id: Int) =
        jdbi.onDemand(ClassroomDAO::class.java).getClassroom(id)

    fun createClassroom(classroomDbWrite: ClassroomDbWrite) =
        jdbi.onDemand(ClassroomDAO::class.java).createClassroom(classroomDbWrite)

    fun updateClassroom(classroomDbUpdate: ClassroomDbUpdate) =
        jdbi.onDemand(ClassroomDAO::class.java).updateClassroom(classroomDbUpdate)
}