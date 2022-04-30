package pt.isel.ion.teams.classrooms

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class ClassroomService(val jdbi: Jdbi) {

    fun getAllClassroomsByOrganization(orgId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomDAO::class.java).getAllClassroomsByOrganization(orgId)
        }

    fun getClassroom(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomDAO::class.java).getClassroom(id)
        }

    fun createClassroom(classroomDbWrite: ClassroomDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomDAO::class.java).createClassroom(classroomDbWrite)
        }

    fun updateClassroom(classroomDbUpdate: ClassroomDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomDAO::class.java).updateClassroom(classroomDbUpdate)
        }
}