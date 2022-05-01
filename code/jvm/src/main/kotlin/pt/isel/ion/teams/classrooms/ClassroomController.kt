package pt.isel.ion.teams.classrooms

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Classrooms.MAIN_PATH)
class ClassroomController(val classroomService: ClassroomService) {

    @GetMapping
    fun getAllClassroomsByOrganization(@PathVariable orgId: Int): List<ClassroomOutputModel> =
        classroomService.getAllClassroomsByOrganization(orgId).map { it.toOutput() }

    @GetMapping(Uris.Classrooms.Classroom.PATH)
    fun getClassroom(@PathVariable classroomId: Int): ClassroomOutputModel =
        classroomService.getClassroom(classroomId).toOutput()

    @PostMapping
    fun createClassroom(@PathVariable orgId: Int,@RequestBody classroomInputModel: ClassroomInputModel) =
        classroomService.createClassroom(classroomInputModel.toDb(orgId))

    @PutMapping
    fun updateClassroom(@RequestBody classroomUpdateModel: ClassroomUpdateModel) =
        classroomService.updateClassroom(classroomUpdateModel.toDb())
}