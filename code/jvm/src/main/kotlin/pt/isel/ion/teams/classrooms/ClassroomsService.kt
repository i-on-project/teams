package pt.isel.ion.teams.classrooms

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

/**
 * Service for the Classroom resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
@Component
class ClassroomsService(val jdbi: Jdbi) {

    fun getAllClassroomsByOrganizationWithPaging(pageSize: Int, pageIndex: Int, orgId: Int, number: Int?) =
        sqlExceptionHandler {
            if (number != null)
                jdbi
                    .onDemand(ClassroomsDAO::class.java)
                    .getAllClassroomsByOrganizationOfTeacherWithPaging(
                        pageSize + 1,
                        pageIndex * pageSize,
                        number,
                        orgId
                    )
            else
                jdbi.onDemand(ClassroomsDAO::class.java)
                    .getAllClassroomsByOrganization(pageSize + 1, pageIndex * pageSize, orgId)

        }

    fun getAllClassroomsByTeacher(teacherNum: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomsDAO::class.java)
                .getAllClassroomsByTeacher(teacherNum)
        }

    fun getClassroom(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomsDAO::class.java).getClassroom(id)
        }

    fun createClassroom(classroomDbWrite: ClassroomDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomsDAO::class.java).createClassroom(classroomDbWrite)
        }

    fun updateClassroom(classroomDbUpdate: ClassroomDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomsDAO::class.java).updateClassroom(classroomDbUpdate)
        }

    fun deleteClassroom(id: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(ClassroomsDAO::class.java).deleteClassroom(id)
        }
}